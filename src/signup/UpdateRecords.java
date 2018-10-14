package signup;
import java.sql.*;

public class UpdateRecords {
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
            conn.close();
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
            conn.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
