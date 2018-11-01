package gui;

import client.CommonClient;
import gui.LoginWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class RequestIPWindows {
    private JPanel mainPanel;
    private JTextField IPtextField;
    private JButton LoginButton;
    static public String ip_server;
    private JFrame this_frame;
    static public CommonClient common_client;

    public RequestIPWindows(JFrame f) {
        this_frame = f;
        LoginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (IPtextField.getText().equals("")){
                    JOptionPane.showMessageDialog(mainPanel, "Please enter your Server's IP");
                }
                else {
                    ip_server = IPtextField.getText();
                    common_client = new CommonClient();
                    String str = common_client.testConnection();
                    if (str == null) {
                        return;
                    }
                    JFrame frame = new JFrame("Nowhere Chat");
                    frame.setContentPane(new LoginWindow(frame).getMainPanel());
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setSize(800, 500);
                    frame.setVisible(true);
                    this_frame.dispose();
                }
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
