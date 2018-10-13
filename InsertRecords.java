package com.codebind;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertRecords {

    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:D://SQLite/Data.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

//    String sql = "CREATE TABLE IF NOT EXISTS usersinfo (\n"
//            + " id integer PRIMARY KEY,\n"
//            + " name text NOT NULL,\n"
//            + " user_name text NOT NULL,\n"
//            + " email text NOT NULL,\n"
//            + " password text NOT NULL,\n"
//            + " gender bool NOT NULL,\n"
//            + " phonenumber int NOT NULL,\n"
//            + " ip_addr text NOT NULL,\n"
//            + " port text NOT NULL,\n"
//            + " status bool NOT NULL\n"
//            + ");";
    public void insert(String name, String user_name,String email,String password,Boolean gender,String phonenumber,String ip_addr,String port,Boolean status) {
        String sql = "INSERT INTO usersinfo(name,user_name,email,password,gender,phonenumber,ip_addr,port,status) VALUES(?,?,?,?,?,?,?,?,?)";
        //System.out.println("ok");
        try{
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, user_name);
            pstmt.setString(3, email);
            pstmt.setString(4, password);
            pstmt.setBoolean(5, gender);
            pstmt.setString(6, phonenumber);
            pstmt.setString(7, ip_addr);
            pstmt.setString(8, port);
            pstmt.setBoolean(9, status);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
// name,user_name,email,password,gender,phonenumber,ip_addr,port,status) VALUES(?,?,?,?,?,?,?,?,?)";

        InsertRecords app = new InsertRecords();
        // insert three new rows
        app.insert("Aryan", "irenren","abc@gmail.com","123123",true,"016728374","198.1.21.2","2134",true);
        app.insert("Daniel", "daniel","abcbc@gmail.com","12a31a23",false,"0167238374","198.1.21.10","2234",true);
        app.insert("Bamm", "binbam","binbam@gmail.com","qwqwqw",true,"0163338374","198.1.33.2","3334",false);


    }

}