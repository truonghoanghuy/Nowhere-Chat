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

    public JPanel getMainPanel(){
        return this.mainPanel;
    }

    public MainWindow(JFrame f) {
        this.this_frame = f;
        tabbedPane.setSelectedIndex(0);
        list_friend.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                contentPanel.removeAll();
                contentPanel.add(new ChatPanel(list_friend.getSelectedValue().toString()).getMainPanel());
                contentPanel.repaint();
                contentPanel.revalidate();
            }
        });
    }

    public static void main(String[] args) {
        /*try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e) {

        }*/
        /*MainWindow window = new MainWindow();
        ChatPanel chat = new ChatPanel();

        window.contentPanel.removeAll();
        window.contentPanel.repaint();
        window.contentPanel.revalidate();

        window.contentPanel.add(chat.getMainPanel());
        window.contentPanel.repaint();
        window.contentPanel.revalidate();

        JFrame frame = new JFrame("Nowhere Chat");
        frame.setContentPane(window.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();*/
    }
}
