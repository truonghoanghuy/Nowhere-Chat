package gui;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginWindow {
    private JPanel panel;
    private JButton signOutButton;
    private JButton logInButton;
    private JTextField textField1;
    private JTextField textField2;
    private JFrame this_frame;

    public LoginWindow(JFrame f) {
        this.this_frame = f;

        logInButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //super.mouseClicked(e);
                //Do something here to verify user and password before going to main window
                showMainWindow();
            }
        });
    }

    private void showMainWindow() {
        JFrame frame = new JFrame("Nowhere Chat");
        frame.setContentPane(new MainWindow(frame).getMainPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        this_frame.dispose();
    }

    public JPanel getMainPanel() {
        return this.panel;
    }
}
