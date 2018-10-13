package signup;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {
    public static void createNewTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:D://MMT/Assignment1/Chat Application/Data.db";

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS usersinfo (\n"
                + " id integer PRIMARY KEY,\n"
                + " name text NOT NULL,\n"
                + " user_name text NOT NULL,\n"
                + " email text NOT NULL,\n"
                + " password text NOT NULL,\n"
                + " gender text NOT NULL,\n"
                + " phonenumber text NOT NULL,\n"
                + " ip_addr text NOT NULL,\n"
                + " port text NOT NULL,\n"
                + " status bool NOT NULL\n"
                + ");";
        try{
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

//    public static void main(String[] args) {
//        createNewTable();
//    }
}

