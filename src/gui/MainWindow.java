package gui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.TreeMap;

import client.MainSocket;
import data.*;
import server.*;

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
    private user user;
    private TreeMap<String, ChatPanel> list_chat_sessions;
    private ArrayList<String> list_chat_conversations;
    private ArrayList<String> list_name_chat_conversations;
    private ArrayList<String> list_onine_friends;
    private ArrayList<Integer> list_port_online_friends;
    private String url;
    private MainSocket main_socket;
    // private JTable friend;

    public JPanel getMainPanel(){
        return this.mainPanel;
    }
    private Connection connect() {
        // SQLite connection string
        //String workingDir = System.getProperty("user.dir");
        //String url = "jdbc:sqlite:" + workingDir + "/Data.db";
        //String url = "jdbc:sqlite:$project_dir$/Data.db";

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    public void friendship(String namecheck1){
        String sql = "SELECT user_name2 as 'User_name' FROM friendship  WHERE user_name1 = '"+ namecheck1+"'";

        try {
            Connection conn = this.connect();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            DefaultListModel dml = new DefaultListModel();
            while (rs.next()){
                String uname = rs.getString("User_name");
                dml.addElement(uname);
            }
            listfriend.setModel(dml);
            //friend.setModel(DbUtils.resultSetToTableModel(rs));

            // loop through the result set

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public MainWindow(JFrame f,user cur_user) {
        this.this_frame = f;
        this.user = cur_user;
        this.nameText.setText(cur_user.getName());
        this.url = "jdbc:sqlite:" + System.getProperty("user.dir") + "/Data.db";
        this.list_chat_conversations = new ArrayList<>();
        this.list_name_chat_conversations = new ArrayList<>();
        this.list_chat_sessions = new TreeMap<>();
        try {
            this.main_socket = new MainSocket(cur_user, list_chat_sessions, list_chat_conversations, list_name_chat_conversations);
        }
        catch (Exception e) {

        }
        main_socket.execute();
        tabbedPane.setSelectedIndex(-1);

        recent_chat.addListSelectionListener(new ListSelectionListener() {
            //@Override
            public void valueChanged(ListSelectionEvent e) {
                ChatPanel chat = list_chat_sessions.get(list_chat_conversations.get(recent_chat.getSelectedIndex()));
                contentPanel.removeAll();
                contentPanel.add(chat.getMainPanel());
                contentPanel.repaint();
                contentPanel.revalidate();
            }
        });

        friendship(cur_user.getName());

        listfriend.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                contentPanel.removeAll();
                SwingWorker worker = new SwingWorker() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        try {
                            int idx = listfriend.getSelectedIndex();
                            if (!list_chat_sessions.containsKey(list_onine_friends.get(idx))) {
                                Socket s = new Socket("localhost", list_port_online_friends.get(idx));
                                PrintStream out = new PrintStream(s.getOutputStream());
                                out.println(cur_user.getUser_name());
                                out.println(cur_user.getName());
                                ChatPanel new_chat = new ChatPanel(listfriend.getSelectedValue().toString(), s,cur_user);
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

        hometxt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Nowhere Chat");
                frame.setContentPane(new LoginWindow(frame).getMainPanel());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 500);
                frame.setVisible(true);
                this_frame.dispose();
            }
        });

        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (tabbedPane.getSelectedIndex() == 0) {
                    showRecentChat();
                }
                else if (tabbedPane.getSelectedIndex() == 1) {
                    showOnlinePeople();
                }
                else if(tabbedPane.getSelectedIndex() == 2) {

                }
            }
        });
    }

    void showOnlinePeople() {
        list_onine_friends = new ArrayList<>();
        list_port_online_friends = new ArrayList<>();
        ((DefaultListModel) listfriend.getModel()).removeAllElements();
        String sql = "SELECT user_name as 'User_name' , name as 'Name' , port as 'Port' FROM usersinfo  WHERE status = 1";
        try {
            Connection conn = this.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String uname = rs.getString("Name");
                String usrname = rs.getString("User_name");
                if (usrname.equals(user.getUser_name()))
                    continue;
                ((DefaultListModel) listfriend.getModel()).addElement(uname);
                list_onine_friends.add(usrname);
                list_port_online_friends.add(Integer.parseInt(rs.getString("Port")));
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    void showRecentChat() {
        DefaultListModel dml = new DefaultListModel();
        for (String name : list_name_chat_conversations) {
            dml.addElement(name);
        }
        recent_chat.setModel(dml);
        listfriend.repaint();
        listfriend.revalidate();
    }

//    static class waiting_request {
//        private ServerSocket wait;
//        private user usr;
//
//        public waiting_request(user u) {
//            this.usr = u;
//        }
//        public void run() {
//            while(true) {
//                try {
//                    wait = new ServerSocket(usr.getPort());
//                }
//                catch (Exception e) {
//
//                }
//            }
//        }
//    }
}
