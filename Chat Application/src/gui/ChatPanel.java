package gui;

import javax.swing.*;
import data.user;

public class ChatPanel {
    private JPanel mainPanel;
    private JButton button1;
    private JTextField textField1;
    private JTextArea textArea1;
    private JLabel friendNameLabel;
    private user user;

    public ChatPanel(String name) {
        this.friendNameLabel.setText(name);
    }

    public JPanel getMainPanel() {
        return this.mainPanel;
    }
}
