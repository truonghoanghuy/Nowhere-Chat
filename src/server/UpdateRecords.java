package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateRecords {
    private Connection conn;

    public UpdateRecords(Connection c) {
        this.conn = c;
    }

    private Connection connect() {
        return this.conn;
    }
    public void updateStatus(String username, boolean stat) {
        String sql = "UPDATE usersinfo SET status = ? WHERE user_name = ?";
        boolean ans = false;
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setBoolean(1, stat);
            pstmt.setString(2, username);
            pstmt.executeUpdate();
            pstmt.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void updateIPandPort(String username, String IP, int p) {
        String sql = "UPDATE usersinfo SET ip_addr = ?, port = ? WHERE user_name = ?";
        try {
            Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, IP);
            pstmt.setString(2, Integer.toString(p));
            pstmt.setString(3, username);
            pstmt.executeUpdate();
            pstmt.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}