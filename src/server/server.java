package server;
import java.lang.reflect.Array;
import java.net.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import data.user;

public class server {
    private Connection conn;
    private SelectRecords sl;
    private UpdateRecords up;
    private InsertRecords ins;
    public server(Connection c) {
        this.conn = c;
        sl = new SelectRecords(this.conn);
        up = new UpdateRecords(this.conn);
        ins = new InsertRecords(this.conn);
    }
    public user checkLogin(String username, String password) {
        return sl.Checklogin(username, password);
    }
    public user findUser(String username) {
        return sl.findUser(username);
    }
    public List<user> selectFriends(String username) {
        return sl.selectFriend(username);
    }
    public void updateIPandPort(String username, String IP, int p) {
        up.updateIPandPort(username, IP, p);
    }
    public void setOnlineStatus(String username, boolean stat) {
        up.updateStatus(username, stat);
    }
    public ArrayList<user> getOnlinePeople(String usrname) {
        return sl.getOnlinePeople(usrname);
    }
    public void insert(String name, String user_name,String email,String password,String gender,
                       String phonenumber,String ip_addr,String port,Boolean status) {
        ins.insert(name, user_name, email, password, gender, phonenumber, ip_addr, port, status);
    }
    public void insertFriend(String username1, String username2) {
        ins.insertfriend(username1, username2);
    }
    public ArrayList<String> getNotFriend(String usrname) {
        return sl.selectNotFriend(usrname);
    }
    public boolean checkFriendShip(String usrname1, String usrname2) {
        return sl.checkfriendship(usrname1, usrname2);
    }
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("This is IP of this server: " + Inet4Address.getLocalHost().getHostAddress());
        ServerSocket theServer = new ServerSocket(7000);
        while (true) {
            Socket sock = theServer.accept();
            new processRequest(sock).run();
        }
    }

    static class processRequest extends Thread {
        Socket sock;

        public processRequest(Socket s) {
            this.sock = s;
        }
        public void run() {
            String workingDir = System.getProperty("user.dir");
            String url = "jdbc:sqlite:" + workingDir + "/Data.db";
            Connection conn = null;
            try {
                conn = DriverManager.getConnection(url);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            server serv = new server(conn);
            try {
                ObjectOutputStream os = new ObjectOutputStream(sock.getOutputStream());
                ObjectInputStream is = new ObjectInputStream(sock.getInputStream());
                ArrayList<String> req = (ArrayList<String>) is.readObject();
                System.out.println("new client is accepted: " + req.get(0));
                if (req.get(0).equals("Test connection")) {
                    //do nothing
                }
                else if (req.get(0).equals("login")) {
                    user canLogin = serv.checkLogin(req.get(1), req.get(2));
                    os.writeObject(canLogin);
                }
                else if (req.get(0).equals("finduser")) {
                    user usr = serv.findUser(req.get(1));
                    os.writeObject(usr);
                }
                else if (req.get(0).equals("selectfriends")) {
                    List<user> frds = serv.selectFriends(req.get(1));
                    os.writeObject(frds);
                }
                else if (req.get(0).equals("updateIpAndPort")) {
                    serv.updateIPandPort(req.get(1), req.get(2), Integer.parseInt(req.get(3)));
                    os.writeObject(null);
                }
                else if (req.get(0).equals("setonlinestatus")) {
                    serv.setOnlineStatus(req.get(1), Boolean.parseBoolean(req.get(2)));
                    os.writeObject(null);
                }
                else if (req.get(0).equals("getOnlinePeople")) {
                    ArrayList<user> people = serv.getOnlinePeople(req.get(1));
                    os.writeObject(people);
                }
                else if (req.get(0).equals("insert")) {
                    serv.insert(req.get(1),
                            req.get(2),
                            req.get(3),
                            req.get(4),
                            req.get(5),
                            req.get(6),
                            req.get(7),
                            req.get(8),
                            Boolean.parseBoolean(req.get(9)));
                    os.writeObject(null);
                }
                else if (req.get(0).equals("insertfriend")) {
                    serv.insertFriend(req.get(1), req.get(2));
                    os.writeObject(null);
                }
                else if (req.get(0).equals("getnotfriend")) {
                    ArrayList<String> people = serv.getNotFriend(req.get(1));
                    os.writeObject(people);
                }
                else if (req.get(0).equals("checkfriendship")) {
                    boolean ans = serv.checkFriendShip(req.get(1), req.get(2));
                    os.writeObject(ans);
                }
                sock.close();
                conn.close();
            }
            catch (Exception e) {
                e.printStackTrace();
                //System.out.println(e.getMessage() + " " + e.getStackTrace());
            }
        }
    }
}