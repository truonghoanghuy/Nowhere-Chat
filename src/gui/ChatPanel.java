package gui;

import javax.swing.*;

import client.Client_Socket;
import data.user;
import server.Server_Socket;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatPanel {
    private ChatPanel this_chat_panel;
    private JPanel mainPanel;
    private JButton button1;
    private JTextField messageTextField;
    private JTextArea textArea1;
    public JTextField textField2;
    public JTextField textField3;
    private JButton connectButton;
    private JLabel friendNameLabel;
    private user user;

    public Server_Socket server;
    public Client_Socket client;

    public boolean isServer = false;
    public boolean isClient = false;

    public ChatPanel(String name) {
        //this.friendNameLabel.setText(name);

        connectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingWorker worker = new SwingWorker() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        if (!textField2.getText().isEmpty()) {
                            client = new Client_Socket(textField2.getText(), Integer.parseInt(textField3.getText()));
                            isClient = true;
                        }
                        else {
                            server = new Server_Socket(textArea1, Integer.parseInt(textField3.getText()));
                            isServer = true;
                        }
                        return null;
                    }
                };
                worker.execute();
            }
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("I send this message!");
                client.sendMessage(messageTextField.getText());
            }
        });
    }
    public JPanel getMainPanel() {
        return this.mainPanel;
    }


}
