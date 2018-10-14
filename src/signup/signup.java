package signup;

import data.user;
import gui.LoginWindow;
import gui.MainWindow;

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
                SelectRecords app = new SelectRecords();
                if (txtusername.getText().isEmpty() | txtName.getText().isEmpty() | txtemail.getText().isEmpty() | txtpassword.getText().isEmpty()| phone.getText().isEmpty()  )
                    JOptionPane.showMessageDialog(null, "Please type full");
                else if(maleRadioButton.isSelected() | femaleRadioButton.isSelected() | otherRadioButton.isSelected()) {
                    if (!app.selectCheck(txtusername.getText()))
                        JOptionPane.showMessageDialog(null, "Username is already exists and used, please type another one");
                    else {
                        InsertRecords ins = new InsertRecords();
                        if (maleRadioButton.isSelected())
                            ins.insert(txtName.getText(), txtusername.getText(), txtemail.getText(), txtpassword.getText(), "Male", phone.getText(), "198.1.21.2", "2134", Boolean.TRUE);
                        else if (femaleRadioButton.isSelected())
                            ins.insert(txtName.getText(), txtusername.getText(), txtemail.getText(), txtpassword.getText(), "Female", phone.getText(), "198.1.21.2", "2134", Boolean.TRUE);
                        else
                            ins.insert(txtName.getText(), txtusername.getText(), txtemail.getText(), txtpassword.getText(), "Other", phone.getText(), "198.1.21.2", "2134", Boolean.TRUE);
                        JOptionPane.showMessageDialog(null, "Successful!");
                        showMainWindow(new SelectRecords().Checklogin(txtusername.getText(), txtpassword.getText()));
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
//    public static void main(String[] args){
//        JFrame frame = new JFrame("Registration");
//        frame.setContentPane(new signup(frame).panelmain);
//        frame.setSize(800,500);
//        //frame.pack();
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setVisible(true);
//
//    }
}
