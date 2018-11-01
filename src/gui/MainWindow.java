package gui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import client.CommonClient;
import client.MainSocket;
import data.*;

public class MainWindow {
    private JFrame this_frame;
    private JPanel mainPanel;
    private JTabbedPane tabbedPane;
    private JPanel contentPanel;
    private JPanel headerPanel;
    private JPanel chatPanel;
    private JPanel peoplePanel;
    private JPanel findPanel;
    private JList recent_chat;
    private JList listfriend;
    private JButton hometxt;
    private JLabel nameText;
    private JList listpeople;
    private JList friends;
    private user user;
    private TreeMap<String, ChatPanel> list_chat_sessions;
    private ArrayList<user> list_recent_chats;
    private ArrayList<user> list_onine_friends;
    private ArrayList<String> list_not_friend;
    private List<user> friendslist;
    private MainSocket main_socket;

    public JPanel getMainPanel(){
        return this.mainPanel;
    }

    public MainWindow(JFrame f,user cur_user) {
        this.this_frame = f;
        this.user = cur_user;
        this.nameText.setText(cur_user.getName());
        this.list_recent_chats = new ArrayList<>();
        this.list_chat_sessions = new TreeMap<>();
        this.list_not_friend = new ArrayList<>();
        CommonClient c = new CommonClient();
        c.setOnlineStatus(cur_user.getUser_name(), true);

        try {
            this.main_socket = new MainSocket(cur_user, list_chat_sessions, list_recent_chats);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(this_frame, e.getMessage());
        }

        try {
            c.updateIPAndPort(user.getUser_name(), InetAddress.getLocalHost().getHostAddress(), this.main_socket.getPort());
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(this_frame, e.getMessage());
        }

        main_socket.execute();
        tabbedPane.setSelectedIndex(-1);

        this_frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                CommonClient c = new CommonClient();
                c.setOnlineStatus(cur_user.getUser_name(), false);
            }
        });

        hometxt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Nowhere Chat");
                frame.setContentPane(new LoginWindow(frame).getMainPanel());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 500);
                frame.setVisible(true);
                CommonClient c = new CommonClient();
                c.setOnlineStatus(cur_user.getUser_name(), false);
                this_frame.dispose();
            }
        });

        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (tabbedPane.getSelectedIndex() == 0) {
                    showRecentChat();
                    listfriend.removeAll();
                    listpeople.removeAll();
                }
                else if (tabbedPane.getSelectedIndex() == 1) {
                    showOnlinePeople();
                    listpeople.removeAll();
                    recent_chat.removeAll();
                }
                else if(tabbedPane.getSelectedIndex() == 2) {
                    showNotFriend();
                }
                else if(tabbedPane.getSelectedIndex() == 3) {
                    showFriends();
                }
            }
        });

        listpeople.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                int input = JOptionPane.showConfirmDialog(null,
                        "Do you want to add " + list_not_friend.get(listpeople.getSelectedIndex()) + " ?",
                        "Select an option...", JOptionPane.YES_NO_OPTION);
                if (input == 0) {
                    //yes
                    CommonClient add = new CommonClient();
                    user frd = add.findUser(list_not_friend.get(listpeople.getSelectedIndex()));
                    add.insertfriend(user.getUser_name(), frd.getUser_name());
                    JOptionPane.showMessageDialog(null,"Add friend successfully!");
                }
            }
        });
        recent_chat.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                ChatPanel chat = list_chat_sessions.get(list_recent_chats.get(recent_chat.getSelectedIndex()).getUser_name());
                contentPanel.removeAll();
                contentPanel.add(chat.getMainPanel());
                contentPanel.repaint();
                contentPanel.revalidate();
            }
        });

        listfriend.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                contentPanel.removeAll();
                SwingWorker worker = new SwingWorker() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        try {
                            int idx = listfriend.getSelectedIndex();
                            if (!list_chat_sessions.containsKey(list_onine_friends.get(idx).getUser_name())) {
                                CommonClient cl = new CommonClient();
                                user usr = cl.findUser(list_onine_friends.get(idx).getUser_name());
                                Socket s = new Socket(usr.getIp_address(), usr.getPort());
                                ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
                                ObjectInputStream in = new ObjectInputStream(s.getInputStream());
                                out.writeObject(user);
                                ChatPanel new_chat = new ChatPanel(listfriend.getSelectedValue().toString(), s, cur_user, out, in);
                                list_chat_sessions.put(list_onine_friends.get(idx).getUser_name(), new_chat);
                                list_recent_chats.add(list_onine_friends.get(idx));
                            }
                            contentPanel.add(list_chat_sessions.get(list_onine_friends.get(idx).getUser_name()).getMainPanel());
                            contentPanel.repaint();
                            contentPanel.revalidate();
                        }
                        catch (Exception exc)  {

                        }
                        return null;
                    }
                };
                worker.execute();
            }
        });
    }

    private void showOnlinePeople() {
        list_onine_friends = new ArrayList<>();
        DefaultListModel model = new DefaultListModel();
        CommonClient ins = new CommonClient();
        ArrayList<user> list = ins.getOnlinePeople(user.getUser_name());
        for (user elem : list) {
            list_onine_friends.add(elem);
            model.addElement(elem.getName());
        }
        listfriend.removeAll();
        listfriend.setModel(model);
    }

    void showRecentChat() {
        recent_chat.removeAll();
        DefaultListModel dml = new DefaultListModel();
        for (user u : list_recent_chats) {
            dml.addElement(u.getName());
        }
        recent_chat.setModel(dml);
    }

    void showNotFriend() {
        list_not_friend = new ArrayList<>();
        DefaultListModel dml = new DefaultListModel();
        CommonClient sl = new CommonClient();
        ArrayList<String> list = sl.getNotFriend(user.getUser_name());
        for (String elem : list) {
            list_not_friend.add(elem);
            user u = sl.findUser(elem);
            dml.addElement(elem + ": " + u.getName());
        }
        listpeople.setModel(dml);
        listpeople.repaint();
        listpeople.revalidate();
    }

    void showFriends() {
        friendslist = new ArrayList<>();
        DefaultListModel dml = new DefaultListModel();
        CommonClient sl = new CommonClient();
        friendslist = sl.selectFriends(user.getUser_name());
        for (user elem : friendslist) {
            dml.addElement(elem.getName());
        }
        friends.setModel(dml);
    }
}
