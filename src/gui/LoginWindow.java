package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import signup.SelectRecords;
import signup.signup;

public class LoginWindow {
    private JPanel panel;
    private JButton signUpButton;
    private JButton logInButton;
    private JTextField username;
    private JPasswordField passwordtxt;
    private JFrame this_frame;

    public LoginWindow(JFrame f) {
        this.this_frame = f;

        logInButton.addMouseListener(new MouseAdapter() {
            //@Override
            public void mouseClicked(MouseEvent e) {
                SelectRecords app = new SelectRecords();
                if (username.getText().isEmpty() | passwordtxt.getText().isEmpty())
                    JOptionPane.showMessageDialog(null,"Please Enter Your User name and Password!");
                else{
<<<<<<< HEAD
                    if(app.Checklogin(username.getText(),passwordtxt.getText()))
                        showMainWindow(username.getText());
=======
                    data.user new_user = app.Checklogin(username.getText(), passwordtxt.getText());
                    if(new_user != null)
                        showMainWindow(new_user);
>>>>>>> master
                    else
                        JOptionPane.showMessageDialog(null,"Please Enter Again!");
                }
                //super.mouseClicked(e);
                //Do something here to verify user and password before going to main window
                //showMainWindow();
            }
        });
        signUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showSignupWindow();
            }
        });
    }
    private void showSignupWindow() {
        JFrame frame = new JFrame("Registration");
        frame.setContentPane(new signup(frame).getMainPanel());
        frame.setSize(800,500);
        //frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        this_frame.dispose();

    }
<<<<<<< HEAD
    private void showMainWindow(String user_name) {
        JFrame frame = new JFrame("Nowhere Chat");
        frame.setContentPane(new MainWindow(frame,user_name).getMainPanel());
=======
    private void showMainWindow(data.user user) {
        JFrame frame = new JFrame("Nowhere Chat");
        frame.setContentPane(new MainWindow(frame,user).getMainPanel());
>>>>>>> master
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setSize(800,500);
        frame.setVisible(true);
        this_frame.dispose();
    }

    public JPanel getMainPanel() {
        return this.panel;
    }
}
