package server;

import data.user;

import javax.swing.*;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

//import java.sql.DriverManager;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
public class SelectRecords {
    private Connection conn;

    public SelectRecords(Connection c) {
        this.conn = c;
    }
    private Connection connect() {
        return this.conn;
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
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Res;
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
            stmt.close();
            rs.close();
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
                return new user(Integer.parseInt(rs.getString("id")),
                                                rs.getString("user_name"),
                                                rs.getString("name"),
                                                rs.getString("gender"),
                                                rs.getString("email"),
                                                rs.getString("phonenumber"),
                                                rs.getString("ip_addr"),
                                                Integer.parseInt(rs.getString("port")),
                                                rs.getBoolean("status"));
            }
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<String> selectNotFriend(String usrname){
        ArrayList<String> notfriend = new ArrayList<>();
        String sql = "SELECT * FROM usersinfo";

        try {
            Connection conn = this.connect();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            // loop through the result set
            while (rs.next()) {
                String u = rs.getString("user_name");
                if (!u.equals(usrname) && !checkfriendship(usrname, u)) {
                    notfriend.add(u);
                }
            }
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return notfriend;
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
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listFriends;
    }

    public ArrayList<String> getOnlinePeople(String usrname) {
        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> res = new ArrayList<>();
        String sql = "SELECT user_name1, user_name2 FROM friendship " +
                "WHERE (user_name1 = '" + usrname + "' OR user_name2 = '" + usrname + "')";
        try {
            Connection conn = this.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String usr1 = rs.getString("user_name1");
                String usr2 = rs.getString("user_name2");
                if(usrname.equals(usr1)) {
                    list.add(usr2);
                }
                else {
                    list.add(usr1);
                }
            }
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        for (String usr : list) {
            try {
                String sql1 = "SELECT user_name, status from usersinfo where (user_name = '" + usr + "' AND status = 1)";
                Connection conn1 = this.connect();
                Statement stmt1 = conn1.createStatement();
                ResultSet rs1 = stmt1.executeQuery(sql1);
                while (rs1.next()) {
                    res.add(rs1.getString("user_name"));
                }
                stmt1.close();
                rs1.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return res;
    }
    public boolean checkfriendship(String usrname1, String usrname2) {
        String sql = "SELECT user_name1, user_name2 FROM friendship " +
                "WHERE (user_name1 = '" + usrname1 +"' AND user_name2 = '" + usrname2 + "') " +
                "OR (user_name1 = '" + usrname2 + "' AND user_name2 = '" + usrname1 +"')";
        try {
            Connection conn = this.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return true;
            }
            stmt.close();
            rs.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
