package gui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import signup.SelectRecords;
import data.*;
import java.util.List;

public class MainWindow {
    //info
    private user owner;
    //do not modify
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

    public void friendship(String username) {
        SelectRecords sl = new SelectRecords();
        List<user> friends = sl.selectFriend(username);
        DefaultListModel<user> dml = new DefaultListModel<>();
        for (user frd : friends) {
            dml.addElement(frd);
        }
        listfriend.setCellRenderer(new UserRenderer());
        listfriend.setModel(dml);
    }

    public MainWindow(JFrame f,String username) {
        SelectRecords sl = new SelectRecords();
        this.owner = sl.findUser(username);
        //this.owner.updateIPandPort(3001);
        this.this_frame = f;
        tabbedPane.setSelectedIndex(0);
        recent_chat.addListSelectionListener(new ListSelectionListener() {
            //@Override
            public void valueChanged(ListSelectionEvent e) {
                ChatPanel chat = new ChatPanel(owner, (user)recent_chat.getSelectedValue());
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
                contentPanel.add(new ChatPanel(owner, (user)listfriend.getSelectedValue()).getMainPanel());
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
