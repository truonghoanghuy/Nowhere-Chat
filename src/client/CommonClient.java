package client;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import data.user;

import java.util.Arrays;
import java.util.List;
import gui.RequestIPWindows;
import javax.swing.*;

public class CommonClient {
    private Socket sock;
    private ObjectOutputStream os;
    private ObjectInputStream is;

    public CommonClient(){
        try {
            this.sock = new Socket(RequestIPWindows.ip_server, 7000);
            this.os = new ObjectOutputStream(sock.getOutputStream());
            this.is = new ObjectInputStream(sock.getInputStream());
        }
        catch (UnknownHostException u) {
            JOptionPane.showMessageDialog(null, "Can't find this Server! Are you sure the Server's IP is correct?");
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public Socket getSocket() {
        return this.sock;
    }

    private Object requestServer(Object obj) {
        Object rs = null;
        try {
            os.writeObject(obj);
            rs = is.readObject();
        }
        catch (UnknownHostException u) {
            JOptionPane.showMessageDialog(null, "Can't find this Server! Are you sure the Server's IP is correct?");
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
    public void updateIPAndPort(String username, String IP, int port) {
        ArrayList<String> req = new ArrayList<>(4);
        req.add("updateIpAndPort");
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
    public ArrayList<user> getOnlinePeople(String usrname) {
        ArrayList<String> req = new ArrayList<>(2);
        req.add("getOnlinePeople");
        req.add(usrname);
        return (ArrayList<user>) this.requestServer(req);
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
    public void closeConnection() {
        ArrayList<String> req = new ArrayList<>(1);
        req.add("close connection");
        try {
            os.writeObject(req);
            this.sock.close();
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    public String testConnection() {
        ArrayList<String> req = new ArrayList<>(1);
        req.add("test connection");
        return (String) this.requestServer(req);
    }

    @Override
    protected void finalize() throws Throwable {
        this.sock.close();
    }
}