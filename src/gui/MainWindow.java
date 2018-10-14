package gui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
    private JButton hometxt;
    // private JTable friend;

    public JPanel getMainPanel(){
        return this.mainPanel;
    }
    private Connection connect() {
        // SQLite connection string
        String workingDir = System.getProperty("user.dir");
        String url = "jdbc:sqlite:" + workingDir + "/Data.db";
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

    public MainWindow(JFrame f,String username) {
        this.this_frame = f;
        tabbedPane.setSelectedIndex(0);
        recent_chat.addListSelectionListener(new ListSelectionListener() {
            //@Override
            public void valueChanged(ListSelectionEvent e) {
                ChatPanel chat = new ChatPanel(recent_chat.getSelectedValue().toString());
                contentPanel.removeAll();
                contentPanel.add(chat.getMainPanel());
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
    }


}
