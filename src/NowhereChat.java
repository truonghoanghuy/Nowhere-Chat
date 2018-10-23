import gui.RequestIPWindows;

import javax.swing.*;

public class NowhereChat {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Nowhere Chat");
        frame.setContentPane(new RequestIPWindows(frame).getMainPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650, 500);
        frame.setVisible(true);
    }
}
