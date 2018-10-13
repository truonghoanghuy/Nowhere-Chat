import gui.LoginWindow;

import javax.swing.*;

public class NowhereChat {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Nowhere Chat");
        frame.setContentPane(new LoginWindow(frame).getMainPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 500);
        frame.setVisible(true);
    }
}
