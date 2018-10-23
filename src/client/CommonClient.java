package client;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import data.user;
import java.util.List;

public class CommonClient {
    private Object requestServer(Object obj) {
        Object rs = null;
        try {
            Socket sock = new Socket("localhost", 8000);
            ObjectOutputStream os = new ObjectOutputStream(sock.getOutputStream());
            ObjectInputStream is = new ObjectInputStream(sock.getInputStream());
            os.writeObject(obj);
            rs = is.readObject();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException c) {
            System.out.println(c.getMessage());
        }
        return rs;
    }
    public user login(String username, String password) {
        ArrayList<String> req = new ArrayList<>(3);
        req.add("login");
        req.add(username);
        req.add(password);
        return (user) this.requestServer(req);
    }
    public user findUser(String username) {
        ArrayList<String> req = new ArrayList<>(2);
        req.add("finduser");
        req.add(username);
        return (user)this.requestServer(req);
    }
    public List<user> selectFriends(String username) {
        ArrayList<String> req = new ArrayList<>(2);
        req.add("selectfriends");
        req.add(username);
        return (List<user>)this.requestServer(req);
    }
    public void setOnlineStatus(String username, boolean stat) {
        ArrayList<String> req = new ArrayList<>(3);
        req.add("setonlinestatus");
        req.add(username);
        req.add(String.valueOf(stat));
        this.requestServer(req);
    }
    public void updateIPandPort(String username, String IP, int port) {
        ArrayList<String> req = new ArrayList<>(4);
        req.add("updateipandport");
        req.add(username);
        req.add(IP);
        req.add(Integer.toString(port));
        this.requestServer(req);
    }
    public void insertfriend(String username1, String username2) {
        ArrayList<String> req = new ArrayList<>(3);
        req.add("insertfriend");
        req.add(username1);
        req.add(username2);
        this.requestServer(req);
    }
    public void insert(String name, String user_name,String email,String password,String gender,
                       String phonenumber,String ip_addr,String port,Boolean status) {
        ArrayList<String> req = new ArrayList<>(10);
        req.add("insert");
        req.add(name);
        req.add(user_name);
        req.add(email);
        req.add(password);
        req.add(gender);
        req.add(phonenumber);
        req.add(ip_addr);
        req.add(port);
        req.add(String.valueOf(status));
        this.requestServer(req);
    }
    public ArrayList<String> getOnlinePeople(String usrname) {
        ArrayList<String> req = new ArrayList<>(2);
        req.add("getOnlinePeople");
        req.add(usrname);
        return (ArrayList<String>) this.requestServer(req);
    }
    public ArrayList<String> getNotFriend(String usrname) {
        ArrayList<String> req = new ArrayList<>(2);
        req.add("getnotfriend");
        req.add(usrname);
        return (ArrayList<String>)this.requestServer(req);
    }
    public boolean checkFriendShip(String usrname1, String usrname2) {
        ArrayList<String> req = new ArrayList<>(3);
        req.add("checkfriendship");
        req.add(usrname1);
        req.add(usrname2);
        return (boolean) this.requestServer(req);
    }
}