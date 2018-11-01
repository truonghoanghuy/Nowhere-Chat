package gui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    public static TreeMap<String, ChatPanel> list_chat_sessions;
    public static ArrayList<user> list_recent_chats;
    private ArrayList<user> list_onine_friends;
    private ArrayList<String> list_not_friend;
    private List<user> friendslist;
    private MainSocket main_socket;
    private CommonClient c;
    SwingWorker w;

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
        RequestIPWindows.common_client.closeConnection();
        this.c = new CommonClient();
        c.setOnlineStatus(cur_user.getUser_name(), true);

//        w = new SwingWorker() {
//            @Override
//            protected Object doInBackground() throws Exception {
//                abc: while(true) {
//                    for (Map.Entry<String, ChatPanel> entry : list_chat_sessions.entrySet()) {
//                        user usr = c.findUser(entry.getKey());
//                        if (!usr.getStatus()) {
//                            list_chat_sessions.remove(entry.getKey());
//                            for (user ur : list_recent_chats) {
//                                if (ur.getUser_name().equals(usr.getUser_name())) {
//                                    list_recent_chats.remove(ur);
//                                    break;
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        };
//        w.execute();
        try {
            this.main_socket = new MainSocket(cur_user, list_chat_sessions, list_recent_chats);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this_frame, e.getMessage());
        }

        try {
            String ip = "";
            try(final DatagramSocket socket = new DatagramSocket()){
                socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
                ip = socket.getLocalAddress().getHostAddress();
                socket.close();
            }
            c.updateIPAndPort(user.getUser_name(), ip, this.main_socket.getPort());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this_frame, e.getMessage());
        }

        main_socket.execute();
        tabbedPane.setSelectedIndex(-1);

        this_frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                c.setOnlineStatus(cur_user.getUser_name(), false);
                close();
            }
        });

        hometxt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Nowhere Chat");
                frame.setContentPane(new LoginWindow(frame).getMainPanel());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 500);
                frame.setVisible(true);
                c.setOnlineStatus(cur_user.getUser_name(), false);
                close();
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
                } else if (tabbedPane.getSelectedIndex() == 1) {
                    showOnlinePeople();
                    listpeople.removeAll();
                    recent_chat.removeAll();
                } else if (tabbedPane.getSelectedIndex() == 2) {
                    showNotFriend();
                } else if (tabbedPane.getSelectedIndex() == 3) {
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
                    user frd = c.findUser(list_not_friend.get(listpeople.getSelectedIndex()));
                    c.insertfriend(user.getUser_name(), frd.getUser_name());
                    JOptionPane.showMessageDialog(null, "Add friend successfully!");
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
                    protected Object doInBackground() throws Exception {
                        try {
                            int idx = listfriend.getSelectedIndex();
                            if (!list_chat_sessions.containsKey(list_onine_friends.get(idx).getUser_name())) {
                                user usr = c.findUser(list_onine_friends.get(idx).getUser_name());
                                Socket s = new Socket(usr.getIp_address(), usr.getPort());
                                ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
                                ObjectInputStream in = new ObjectInputStream(s.getInputStream());
                                out.writeObject(user);
                                ChatPanel new_chat = new ChatPanel(listfriend.getSelectedValue().toString(), s, cur_user, usr, out, in);
                                list_chat_sessions.put(list_onine_friends.get(idx).getUser_name(), new_chat);
                                list_recent_chats.add(usr);
                            }
                            contentPanel.add(list_chat_sessions.get(list_onine_friends.get(idx).getUser_name()).getMainPanel());
                            contentPanel.repaint();
                            contentPanel.revalidate();
                        } catch (Exception exc) {
                            JOptionPane.showMessageDialog(this_frame, exc.getMessage());
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
        ArrayList<user> list = c.getOnlinePeople(user.getUser_name());
        for (user elem : list) {
            list_onine_friends.add(elem);
            model.addElement(elem.getName());
        }
        listfriend.removeAll();
        listfriend.setModel(model);
    }

    private void showRecentChat() {
        recent_chat.removeAll();
        DefaultListModel dml = new DefaultListModel();
        for (user u : list_recent_chats) {
            dml.addElement(u.getName());
        }
        recent_chat.setModel(dml);
    }

    private void showNotFriend() {
        list_not_friend = new ArrayList<>();
        DefaultListModel dml = new DefaultListModel();
        ArrayList<String> list = c.getNotFriend(user.getUser_name());
        for (String elem : list) {
            list_not_friend.add(elem);
            user u = c.findUser(elem);
            dml.addElement(elem + ": " + u.getName());
        }
        listpeople.setModel(dml);
        listpeople.repaint();
        listpeople.revalidate();
    }

    private void showFriends() {
        friendslist = new ArrayList<>();
        DefaultListModel dml = new DefaultListModel();
        friendslist = c.selectFriends(user.getUser_name());
        for (user elem : friendslist) {
            dml.addElement(elem.getName());
        }
        friends.setModel(dml);
    }

    void close() {
        c.closeConnection();
        //w.cancel(true);
        try {
            for (Map.Entry<String, ChatPanel> entry : list_chat_sessions.entrySet()) {
                entry.getValue().getSocket().close();
            }
            main_socket.closeMainSocket();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
