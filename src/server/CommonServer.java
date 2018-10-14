package server;
import java.net.*;
import java.io.*;

import signup.InsertRecords;
import signup.SelectRecords;
import data.user;
import signup.UpdateRecords;

import java.util.List;
import java.util.ArrayList;

public class CommonServer {
    private SelectRecords sl;
    private UpdateRecords up;
    private InsertRecords in;
    public CommonServer() {
        sl = new SelectRecords();
        up = new UpdateRecords();
        in = new InsertRecords();
    }
    public boolean checkLogin(String username, String password) {
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
    public void insertfriend(String username1, String username2) {
        in.insertfriend(username1, username2);
    }
    public void insert(String name, String user_name,String email,String password,String gender,
                       String phonenumber,String ip_addr,String port,Boolean status) {
        in.insert(name, user_name, email, password, gender, phonenumber, ip_addr, port, status);
    }
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        CommonServer serv = new CommonServer();
        ServerSocket theServer = new ServerSocket(6000);
        while (true) {
            Socket sock = theServer.accept();
            ObjectOutputStream os = new ObjectOutputStream(sock.getOutputStream());
            ObjectInputStream is = new ObjectInputStream(sock.getInputStream());
            ArrayList<String> req = (ArrayList<String>) is.readObject();
            if (req.get(0).equals("login")) {
                boolean canLogin = serv.checkLogin(req.get(1), req.get(2));
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
            else if (req.get(0).equals("updateipandport")) {
                serv.updateIPandPort(req.get(1), req.get(2), Integer.parseInt(req.get(3)));
                os.writeObject(null);
            }
            else if (req.get(0).equals("setonlinestatus")) {
                serv.setOnlineStatus(req.get(1), Boolean.parseBoolean(req.get(2)));
                os.writeObject(null);
            }
            else if (req.get(0).equals("insertfriend")) {
                serv.insertfriend(req.get(1), req.get(2));
                os.writeObject(null);
            }
            else if (req.get(0).equals("insert")) {
                serv.insert(req.get(1), req.get(2), req.get(3), req.get(4), req.get(5),
                        req.get(6), req.get(7), req.get(8), Boolean.parseBoolean(req.get(9)));
                os.writeObject(null);
            }
            sock.close();
        }
    }
}
