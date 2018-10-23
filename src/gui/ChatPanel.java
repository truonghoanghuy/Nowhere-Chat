package gui;

import javax.swing.*;

import data.user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ChatPanel {
    private ChatPanel this_chat_panel;
    private JPanel mainPanel;
    private JButton sendButton;
    private JTextField messageTextField;
    private JTextArea textArea;
    private JLabel friendNameLabel;
    private user user;
    private BufferedReader br;
    private PrintStream pr;
    private Socket socket;

    public ChatPanel(String name, Socket socket, user this_user) {
        this.friendNameLabel.setText(name);
        this.socket = socket;
        this.user = this_user;

        try {
            this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.pr = new PrintStream(socket.getOutputStream());
        }
        catch (Exception e) {

        }

        receive_message receiving = new receive_message(br, textArea);
        receiving.execute();

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pr.println(user.getName() + ": " + messageTextField.getText());
                textArea.append(user.getName() + ": " + messageTextField.getText()+ "\n");
                messageTextField.setText("");
            }
        });
    }
    public JPanel getMainPanel() {
        return this.mainPanel;
    }

    static class receive_message extends SwingWorker {
        private BufferedReader br;
        JTextArea txta;

        public receive_message(BufferedReader br, JTextArea txta) {
            this.br = br;
            this.txta = txta;
        }
        @Override
        protected Void doInBackground() throws Exception {
            while (true) {
                String temp = br.readLine();
                txta.append(temp + "\n");
            }
        }
    }
}