package gui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
    // private JTable friend;

    public JPanel getMainPanel(){
        return this.mainPanel;
    }
    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:D://MMT/Assignment1/Chat Application/Data.db";
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
    }


}
