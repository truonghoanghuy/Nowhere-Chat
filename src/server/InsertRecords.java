package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertRecords {
    private Connection conn;

    public InsertRecords(Connection c) {
        this.conn = c;
    }

    private Connection connect() {
        return this.conn;
    }

    public void insert(String name, String user_name,String email,String password,String gender,String phonenumber,String ip_addr,String port,Boolean status) {
        String sql = "INSERT INTO usersinfo(name,user_name,email,password,gender,phonenumber,ip_addr,port,status) VALUES(?,?,?,?,?,?,?,?,?)";
        //System.out.println("ok");
        try{
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, user_name);
            pstmt.setString(3, email);
            pstmt.setString(4, password);
            pstmt.setString(5, gender);
            pstmt.setString(6, phonenumber);
            pstmt.setString(7, ip_addr);
            pstmt.setString(8, port);
            pstmt.setBoolean(9, status.booleanValue());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void insertfriend(String user_name1,String user_name2) {
        String sql = "INSERT INTO friendship(user_name1,user_name2) VALUES(?,?)";
        try{
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user_name1);
            pstmt.setString(2, user_name2);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
