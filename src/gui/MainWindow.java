package gui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.sql.*;
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
    private JTextField input;
    private JButton Find;
    // private JTable friend;

    public JPanel getMainPanel(){
        return this.mainPanel;
    }
    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:D://MMT/BTL1/Nowhere-Chat-develop1/Data.db";
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

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public MainWindow(JFrame f, final String username) {
        this.this_frame = f;
        tabbedPane.setSelectedIndex(0);
        recent_chat.addListSelectionListener(new ListSelectionListener() {
            //@Override
            public void valueChanged(ListSelectionEvent e) {
                contentPanel.removeAll();
                contentPanel.add(new ChatPanel(recent_chat.getSelectedValue().toString()).getMainPanel());
                contentPanel.repaint();
                contentPanel.revalidate();
            }
        });
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
                System.out.println("index="+list.getSelectedIndex());
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
                System.out.println("index="+list.getSelectedIndex());
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



        });
    }


}
