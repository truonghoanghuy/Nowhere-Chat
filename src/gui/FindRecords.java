//import javax.swing.*;
package gui;
import java.sql.*;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
public class FindRecords{
    //private JPanel findPanel;
    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:D://MMT/BTL1/Data.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }


    public DefaultListModel select(String user_name){
        String sql = "SELECT * FROM usersinfo " + "WHERE user_name = ?";

        DefaultListModel listModel;
        listModel = new DefaultListModel();
        JList list;
        try {
            Connection conn = this.connect();
            //Statement stmt  = conn.createStatement();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, user_name);
            ResultSet rs    = stmt.executeQuery();
            if (!rs.isBeforeFirst() ) {
                System.out.println("No data");
            }
            //System.out.println("ab");
            //System.out.println(rs.getString("user_name"));
            /*while (rs.next()) {
                System.out.println(rs.getInt("id") +  "\t" +
                        rs.getString("name") + "\t" +
                        rs.getString("user_name") + "\t"+
                        rs.getString("email") + "\t"+
                        rs.getString("password") + "\t"+
                        rs.getBoolean("gender") + "\t"+
                        rs.getString("phonenumber")+ "\t"+
                        rs.getString("ip_addr") + "\t"+
                        rs.getString("port")+ "\t"+
                        rs.getBoolean("status"));
            }*/
            // loop through the result set
            while (rs.next()) {
                listModel.addElement(rs.getString("user_name"));
                //System.out.println("a");
                System.out.println(rs.getString("user_name"));
            }
            //System.out.println("ac");
            /*for (int i = 0; i < listModel.size(); i++) {
                System.out.println(listModel.get(i));
            };*/

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            //System.out.println("aa");
        }


        return listModel;


    }
    public void display(DefaultListModel listModel){
        //FindRecords listModel = new FindRecords();

    }
}