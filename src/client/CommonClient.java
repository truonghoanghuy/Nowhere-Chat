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
            Socket sock = new Socket("localhost", 6000);
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
    public boolean login(String username, String password) {
        ArrayList<String> req = new ArrayList<>(3);
        req.add("login");
        req.add(username);
        req.add(password);
        return (boolean)this.requestServer(req);
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
}
