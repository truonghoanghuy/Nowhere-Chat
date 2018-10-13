package data;

import java.io.IOException;
import java.net.InetAddress;

public class user {
    int user_id;
    String user_name;
    String name;
    String gender;
    String email;
    String phone_number;
    String ip_address;
    String hostName;
    int port;
    Boolean status; // true is online, false is offline
    public user(int p) {
        try {
            InetAddress x = InetAddress.getLocalHost();
            ip_address = x.getHostAddress();
            hostName = x.getHostName();
            port = p;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getName(){
        return user_name;
    }
    public int getPort() {
        return port;
    }
    public String getIP() {
        return ip_address;
    }
    public int getUserID(){
        return user_id;
    }
}
