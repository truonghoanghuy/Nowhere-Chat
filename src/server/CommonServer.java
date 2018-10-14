package server;
import java.net.*;
import java.io.*;
import signup.SelectRecords;
import data.user;
import java.util.List;
import java.util.ArrayList;

public class CommonServer {
    private SelectRecords sl;
    public CommonServer() {
        sl = new SelectRecords();
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
        sl.updateIPandPort(username, IP, p);
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
            }
            sock.close();
        }
    }
}
