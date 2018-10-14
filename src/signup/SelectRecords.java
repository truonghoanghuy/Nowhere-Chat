package signup;

import java.sql.*;
import data.user;
//import java.sql.DriverManager;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
public class SelectRecords {
    private Connection connect() {
        // SQLite connection string
<<<<<<< HEAD
        String url = "jdbc:sqlite:D://MMT/BTL1/Nowhere-Chat-develop1/Data.db";
=======
        String workingDir = System.getProperty("user.dir");
        String url = "jdbc:sqlite:" + workingDir + "/Data.db";
>>>>>>> master
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }


    public boolean selectCheck(String namecheck){
        String sql = "SELECT * FROM usersinfo  WHERE user_name = '"+ namecheck+"'";

        try {
            Connection conn = this.connect();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            // loop through the result set
            while (rs.next()) {
//                System.out.println(rs.getInt("id") +  "\t" +
//                        rs.getString("name") + "\t" +
//                        rs.getString("user_name") + "\t"+
//                        rs.getString("email") + "\t"+
//                        rs.getString("password") + "\t"+
//                        rs.getBoolean("gender") + "\t"+
//                        rs.getString("phonenumber")+ "\t"+
//                        rs.getString("ip_addr") + "\t"+
//                        rs.getString("port")+ "\t"+
//                        rs.getBoolean("status"));
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public user Checklogin(String namecheck,String passw){
        String sql = "SELECT * FROM usersinfo  WHERE user_name = '"+ namecheck+"' and password = '"+passw+"'";

        try {
            Connection conn = this.connect();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            // loop through the result set
            while (rs.next()) {
//                System.out.println(rs.getInt("id") +  "\t" +
//                        rs.getString("name") + "\t" +
//                        rs.getString("email") + "\t"+
//                        rs.getString("user_name") + "\t"+
//                        rs.getString("password") + "\t"+
//                        rs.getString("gender") + "\t"+
//                        rs.getString("phonenumber")+ "\t"+
//                        rs.getString("ip_addr") + "\t"+
//                        rs.getString("port")+ "\t"+
//                        rs.getBoolean("status"));
                return new user(Integer.parseInt(rs.getString("id")),
                                                rs.getString("user_name"),
                                                rs.getString("name"),
                                                rs.getString("gender"),
                                                rs.getString("email"),
                                                rs.getString("phonenumber"),
                                                Integer.parseInt(rs.getString("port")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }



    public void selectAll(){
        String sql = "SELECT * FROM usersinfo";

        try {
            Connection conn = this.connect();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("id") +  "\t" +
                        rs.getString("name") + "\t" +
                        rs.getString("user_name") + "\t"+
                        rs.getString("email") + "\t"+
                        rs.getString("password") + "\t"+
                        rs.getString("gender") + "\t"+
                        rs.getString("phonenumber")+ "\t"+
                        rs.getString("ip_addr") + "\t"+
                        rs.getString("port")+ "\t"+
                        rs.getBoolean("status"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

//    public static void main(String[] args) {
//        SelectRecords app = new SelectRecords();
//        app.selectAll();
//    }

//    public static void main(String[] args) {
//        SelectRecords add = new SelectRecords();
//        System.out.println(add.Checklogin("thanhnhat123","thanhnhat123"));
//    }
}
