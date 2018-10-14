package signup;

import java.sql.*;
//import java.sql.DriverManager;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
import com.sun.jdi.event.StepEvent;
import data.user;

import java.util.ArrayList;
import java.util.List;


public class SelectRecords {
    private Connection connect() {
        // SQLite connection string
        String workingDir = System.getProperty("user.dir");
        String url = "jdbc:sqlite:" + workingDir + "/Data.db";
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
        boolean ans = true;
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
                ans = false;
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ans;
    }

    public boolean Checklogin(String namecheck,String passw){
        String sql = "SELECT * FROM usersinfo  WHERE user_name = '"+ namecheck+"' and password = '"+passw+"'";
        boolean ans = false;
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
                ans = true;
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ans;
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
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public user findUser(String username) {
        String sql = "SELECT * FROM usersinfo WHERE user_name = '" + username + "'";
        user Res = null;
        try {
            Connection conn = this.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Res = new user(rs.getInt("id"),
                        rs.getString("user_name"),
                        rs.getString("name"),
                        rs.getString("gender"),
                        rs.getString("email"),
                        rs.getString("phonenumber"),
                        rs.getString("ip_addr"),
                        rs.getInt("port"),
                        rs.getBoolean("status"));
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Res;
    }

    public List<user> selectFriend(String username) {
        String sql = "SELECT * FROM friendship WHERE user_name1 ='" + username + "' or user_name2 = '" + username + "'";
        List<user> listFriends = new ArrayList<user>();
        try {
            Connection conn = this.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            // loop through result set
            while (rs.next()) {
                if (rs.getString("user_name1").equals(username)) {
                    user newU = findUser(rs.getString("user_name2"));
                    if (newU != null) {
                        listFriends.add(newU);
                    }
                } else {
                    user newU = findUser(rs.getString("user_name1"));
                    if (newU != null) {
                        listFriends.add(newU);
                    }
                }
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listFriends;
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
