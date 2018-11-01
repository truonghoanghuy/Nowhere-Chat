package gui;

import javax.swing.*;

import data.user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.Socket;
import javax.swing.JFileChooser;

public class ChatPanel {
    private ChatPanel this_chat_panel;
    private JPanel mainPanel;
    private JButton sendButton;
    private JTextField messageTextField;
    private JTextArea textArea;
    private JLabel friendNameLabel;
    private JButton chooseFileButton;
    private user user;
    private user friend;
    //private BufferedReader br;
    //private PrintStream pr;
    private ObjectInputStream br;
    private ObjectOutputStream pr;
    private Socket socket;

    public ChatPanel(String name, Socket socket, user this_user, user friend, ObjectOutputStream out, ObjectInputStream in) {
        this.friendNameLabel.setText(name);
        this.socket = socket;
        this.user = this_user;
        this.pr = out;
        this.br = in;
        this.friend = friend;

        receive_message receiving = new receive_message(br, pr, textArea);
        receiving.execute();

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                send();
            }
        });

        chooseFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File f = pickfile();
                try {
                    dataSocket obj = new dataSocket(f, user.getName());
                    pr.writeObject(obj);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                textArea.append(user.getName() + ": " + "Sending file " + f.getName() + " ..." + "\n");
            }
        });

        messageTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    send();
                }
            }
        });
    }

    public JPanel getMainPanel() {
        return this.mainPanel;
    }

    public Socket getSocket(){
        return this.socket;
    }

    private void send() {
        try {
            dataSocket obj = new dataSocket(user.getName() + ": " + messageTextField.getText());
            pr.writeObject(obj);
        }
        catch (IOException ex) {
            JOptionPane.showMessageDialog(mainPanel, "Oops, this user is no longer online. So you can't send message to this user");
            messageTextField.setText("");
            if (MainWindow.list_chat_sessions.containsKey(friend.getUser_name()))
                MainWindow.list_chat_sessions.remove(friend.getUser_name());
            for (user u : MainWindow.list_recent_chats) {
                if (u.getUser_name().equals(friend.getUser_name())) {
                    MainWindow.list_recent_chats.remove(u);
                    break;
                }
            }
            return;
        }
        textArea.append(user.getName() + ": " + messageTextField.getText()+ "\n");
        messageTextField.setText("");
    }

    static class receive_message extends SwingWorker {
        //private BufferedReader br;
        private ObjectInputStream br;
        private ObjectOutputStream pr;
        JTextArea txta;

        public receive_message(/*BufferedReader*/ObjectInputStream br, ObjectOutputStream pr, JTextArea txta) {
            this.br = br;
            this.pr = pr;
            this.txta = txta;
        }
        @Override
        protected Void doInBackground() throws Exception {
            while (true) {
                dataSocket temp = (dataSocket)br.readObject();
                if (!temp.getFlag()) { //false is for text
                    txta.append(temp.getText() + "\n");
                }
                else {
                    //show dialog
                    txta.append(temp.getText() + "\n");
                    int input = JOptionPane.showConfirmDialog(null,
                            "Do you want to receive file " + temp.getFileName() + "?",
                            temp.getName() + " wants to file to you...", JOptionPane.YES_NO_OPTION);
                    if (input == 0) {
                        //receive file
                        byte[] mybytearray = temp.getData();
                        JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                        int result = fileChooser.showSaveDialog(null);
                        if (result == JFileChooser.APPROVE_OPTION) {
                            File file = fileChooser.getSelectedFile();
                            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                            bos.write(mybytearray, 0, mybytearray.length);
                            bos.close();
                        }
                        else {
                            //send message back to tell the file is denied
                            dataSocket msg = new dataSocket("File is not accepted.");
                            pr.writeObject(msg);
                            txta.append("File is not accepted.\n");
                        }
                    }
                    else {
                        //send message back to tell the file is denied
                        dataSocket msg = new dataSocket("File is not accepted.");
                        pr.writeObject(msg);
                        txta.append("File is not accepted.\n");
                    }
                }
            }
        }
    }

    private File pickfile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        else {
            return null;
        }
    }
}
