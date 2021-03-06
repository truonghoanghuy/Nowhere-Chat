package signup;

import client.CommonClient;
import gui.LoginWindow;
import gui.MainWindow;
import gui.RequestIPWindows;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class signup {
    private JLabel label1;
    private JTextField txtemail;
    private JTextField txtName;
    private JLabel label2;
    private JTextField txtusername;
    private JLabel label3;
    private JPasswordField txtpassword;
    private JRadioButton maleRadioButton;
    private JRadioButton otherRadioButton;
    private JTextField phone;
    private JButton button1;
    private JRadioButton femaleRadioButton;
    private JButton signUpButton;
    private JPanel panelmain;
    private JFrame this_frame;

    public signup(JFrame f) {
        this.this_frame = f;

        signUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CommonClient ins = RequestIPWindows.common_client;
                if (txtusername.getText().isEmpty() | txtName.getText().isEmpty() | txtemail.getText().isEmpty() | txtpassword.getText().isEmpty()| phone.getText().isEmpty()  )
                    JOptionPane.showMessageDialog(null, "Please type full");
                else if(maleRadioButton.isSelected() | femaleRadioButton.isSelected() | otherRadioButton.isSelected()) {
                    if (ins.findUser(txtusername.getText()) != null)
                        JOptionPane.showMessageDialog(null, "Username is already exists and used, please type another one");
                    else {
                        if (maleRadioButton.isSelected())
                            ins.insert(txtName.getText(), txtusername.getText(), txtemail.getText(), txtpassword.getText(), "Male", phone.getText(), "", "1234", Boolean.TRUE);
                        else if (femaleRadioButton.isSelected())
                            ins.insert(txtName.getText(), txtusername.getText(), txtemail.getText(), txtpassword.getText(), "Female", phone.getText(), "", "1234", Boolean.TRUE);
                        else
                            ins.insert(txtName.getText(), txtusername.getText(), txtemail.getText(), txtpassword.getText(), "Other", phone.getText(), "", "1234", Boolean.TRUE);
                        JOptionPane.showMessageDialog(null, "Successful!");
                        showMainWindow(ins.login(txtusername.getText(), txtpassword.getText()));
                    }
                }
                else
                    JOptionPane.showMessageDialog(null, "Your gender? :))");
            }
        });

        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("HOME");
                frame.setContentPane(new LoginWindow(frame).getMainPanel());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 500);
                frame.setVisible(true);
                this_frame.dispose();

            }
        });
    }

    public JPanel getMainPanel() {
        return this.panelmain;
    }

    private void showMainWindow(data.user usr) {
        JFrame frame = new JFrame("Nowhere Chat");
        frame.setContentPane(new MainWindow(frame, usr).getMainPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,500);
        frame.setVisible(true);
        this_frame.dispose();
    }
}
