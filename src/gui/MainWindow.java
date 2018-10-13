package gui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class MainWindow {
    private JFrame this_frame;
    private JPanel mainPanel;
    private JTabbedPane tabbedPane;
    private JPanel contentPanel;
    private JPanel headerPanel;
    private JPanel chatPanel;
    private JPanel peoplePanel;
    private JPanel findPanel;
    private JList list_friend;
    private JTable table1;

    public JPanel getMainPanel(){
        return this.mainPanel;
    }

    public MainWindow(JFrame f) {
        this.this_frame = f;
        tabbedPane.setSelectedIndex(0);
        list_friend.addListSelectionListener(new ListSelectionListener() {
            //@Override
            public void valueChanged(ListSelectionEvent e) {
                contentPanel.removeAll();
                contentPanel.add(new ChatPanel(list_friend.getSelectedValue().toString()).getMainPanel());
                contentPanel.repaint();
                contentPanel.revalidate();
            }
        });
    }


}
