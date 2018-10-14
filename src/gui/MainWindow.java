package gui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
<<<<<<< HEAD
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.sql.*;
=======
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

>>>>>>> master
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
<<<<<<< HEAD
    private JTextField input;
    private JButton Find;
=======
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
>>>>>>> master
    // private JTable friend;

    public JPanel getMainPanel(){
        return this.mainPanel;
    }
    private Connection connect() {
        // SQLite connection string
<<<<<<< HEAD
        String url = "jdbc:sqlite:D://MMT/BTL1/Nowhere-Chat-develop1/Data.db";
=======
        //String workingDir = System.getProperty("user.dir");
        //String url = "jdbc:sqlite:" + workingDir + "/Data.db";
        //String url = "jdbc:sqlite:$project_dir$/Data.db";

>>>>>>> master
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
<<<<<<< HEAD

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
    public void insertreq(String req_name, String acc_name) {
        String sql = "INSERT INTO requests (req_name,acc_name) VALUES(?,?)";;
        //System.out.println("ok");
        try{
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, req_name);
            pstmt.setString(2, acc_name);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void insertfriend(String user_name1, String user_name2) {
        String sql = "INSERT INTO friendship (user_name1,user_name2) VALUES(?,?)";;
        //System.out.println("ok");
        try{
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user_name1);
            pstmt.setString(2, user_name2);

=======

            DefaultListModel dml = new DefaultListModel();
            while (rs.next()){
                String uname = rs.getString("User_name");
                dml.addElement(uname);
            }
            listfriend.setModel(dml);
            //friend.setModel(DbUtils.resultSetToTableModel(rs));

            // loop through the result set

>>>>>>> master
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

<<<<<<< HEAD
    public MainWindow(JFrame f, final String username) {
        this.this_frame = f;
        tabbedPane.setSelectedIndex(0);
        recent_chat.addListSelectionListener(new ListSelectionListener() {
            //@Override
            public void valueChanged(ListSelectionEvent e) {
=======
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
>>>>>>> master
                contentPanel.removeAll();
                contentPanel.add(new ChatPanel(recent_chat.getSelectedValue().toString()).getMainPanel());
                contentPanel.repaint();
                contentPanel.revalidate();
            }
        });
<<<<<<< HEAD
        friendship(username);
        listfriend.addMouseListener(new MouseAdapter() {
        });
        listfriend.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                contentPanel.removeAll();
                contentPanel.add(new ChatPanel(listfriend.getSelectedValue().toString()).getMainPanel());
                contentPanel.repaint();
                contentPanel.revalidate();
            }
        });
        Find.addActionListener(new ActionListener() {
            private JList list;
            public void actionPerformed(ActionEvent e) {
                JFrame nef= new JFrame();
                String x = input.getText();
                //System.out.println(x);
                FindRecords listm = new FindRecords();
                DefaultListModel listModel;
                listModel=listm.select(x);
                /*for (int i = 0; i < listModel.size(); i++) {
                    System.out.println(listModel.get(i));
                };*/

                list = new JList(listModel);
                JScrollPane lists = new JScrollPane(list);
                lists.setBounds(0,0, 400,300);
                nef.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                nef.add(lists);
                nef.setSize(400,400);
                nef.setLayout(null);
                nef.setVisible(true);
                list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                //System.out.println("index="+list.getSelectedIndex());
                list.setSelectedIndex(0);
                //list.addListSelectionListener(this);
                /*list.addListSelectionListener(new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent e) {
                        //int s = list.getSelectedIndex();
                        if(e.getValueIsAdjusting()) {
                            return;
                        }
                        //fireStateChanged();
                    };
                });*/
                //System.out.println("index="+list.getSelectedIndex());
                JButton hireButton = new JButton("Add");
                hireButton.setActionCommand("Add");
                hireButton.addActionListener(new AddListener());
                //nef.setLayout(new BorderLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridwidth = GridBagConstraints.REMAINDER;
                //add(btn, gbc);
                //nef.setLayout(null);
                hireButton.setBounds(300,300,75,40);
                nef.add(hireButton);
                //findPanel.show();
                //findPanel.getContentPane().add(courseJList);
                //findPanel.add(new JScrollPane(list));
            }
            class AddListener implements ActionListener {
                public void actionPerformed(ActionEvent e) {
                    String acc_name = (list.getModel().getElementAt(0)).toString();
                    insertfriend(username,acc_name);
                    insertfriend(acc_name,username);
                    JOptionPane.showMessageDialog(null,"Da duoc ket ban");
                    //User didn't type in a name...

                }
            }



=======

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
>>>>>>> master
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
