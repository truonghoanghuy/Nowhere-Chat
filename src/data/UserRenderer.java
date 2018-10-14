package data;

import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class UserRenderer extends JLabel implements ListCellRenderer<user> {
    public UserRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends user> list, user usr, int index,
                                                  boolean isSelected, boolean cellHasFocus) {

        String online = "online";
        if (!usr.getStatus()) {
            online = "offline";
        }
        String workingDir = System.getProperty("user.dir");
        String url = workingDir + "/icons/";
        ImageIcon imageIcon = new ImageIcon(url + online + ".png");

        setIcon(imageIcon);
        setText(usr.getName());

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        return this;
    }
}
