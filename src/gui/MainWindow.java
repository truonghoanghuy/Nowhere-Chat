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
    private ArrayList<String> list_chat_conversations;
    private ArrayList<String> list_name_chat_conversations;
    private ArrayList<String> list_onine_friends;
    private ArrayList<Integer> list_port_online_friends;
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
        this.list_chat_conversations = new ArrayList<>();
        this.list_name_chat_conversations = new ArrayList<>();
        this.list_chat_sessions = new TreeMap<>();
        this.list_not_friend = new ArrayList<>();
        CommonClient c = new CommonClient();
        c.setOnlineStatus(cur_user.getUser_name(), true);

        try {
            this.main_socket = new MainSocket(cur_user, list_chat_sessions, list_chat_conversations, list_name_chat_conversations);
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
                ChatPanel chat = list_chat_sessions.get(list_chat_conversations.get(recent_chat.getSelectedIndex()));
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
                            if (!list_chat_sessions.containsKey(list_onine_friends.get(idx))) {
                                Socket s = new Socket("localhost", list_port_online_friends.get(idx));
                                ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
                                ObjectInputStream in = new ObjectInputStream(s.getInputStream());
                                ArrayList<String> req = new ArrayList<>(2);
                                req.add(cur_user.getUser_name());
                                req.add(cur_user.getName());
                                out.writeObject(req);
                                ChatPanel new_chat = new ChatPanel(listfriend.getSelectedValue().toString(), s, cur_user, out, in);
                                list_chat_sessions.put(list_onine_friends.get(idx), new_chat);
                                list_chat_conversations.add(list_onine_friends.get(idx));
                                list_name_chat_conversations.add(listfriend.getSelectedValue().toString());
                            }
                            contentPanel.add(list_chat_sessions.get(list_onine_friends.get(idx)).getMainPanel());
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

    void showOnlinePeople() {
        list_onine_friends = new ArrayList<>();
        list_port_online_friends = new ArrayList<>();
        DefaultListModel model = new DefaultListModel();
        CommonClient ins = new CommonClient();
        ArrayList<String> list = ins.getOnlinePeople(user.getUser_name());
        for (String elem : list) {
            user usr = ins.findUser(elem);
            //if (usr.getUser_name().equals(user.getUser_name()))
                //continue;
            list_onine_friends.add(elem);
            list_port_online_friends.add(usr.getPort());
            model.addElement(usr.getName());
        }
        listfriend.setModel(model);
    }

    void showRecentChat() {
        DefaultListModel dml = new DefaultListModel();
        for (String name : list_name_chat_conversations) {
            dml.addElement(name);
        }
        recent_chat.setModel(dml);
//        listfriend.repaint();
//        listfriend.revalidate();
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
