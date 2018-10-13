package server;
import java.net.*;
import java.io.*;
import data.user;
import java.util.List;

/*
This class is for receiving socket requests to open new chat sessions
 */

public class ReceiveRequest implements Runnable {
    private ServerSocket theServer;
    private user host;
    private user frd;
    private List<user> onlineFriends;

    public ReceiveRequest(user owner, List<user> onlFrds) {
        host = owner;
        onlineFriends = onlFrds;
    }
    private user findUser(int user_id) {
        //find user with id
        for (user friend : onlineFriends) {
            if (friend.getUserID() == user_id){
                return friend;
            }
        }
        return null;
    }
    public void run() {
        try {
            theServer = new ServerSocket(host.getPort());
            while(true) {
                Socket sock = theServer.accept();
                DataInputStream din = new DataInputStream(sock.getInputStream());
                DataOutputStream dout = new DataOutputStream(sock.getOutputStream());
                frd = findUser(din.readInt());
                //create chat session with server_socket
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
