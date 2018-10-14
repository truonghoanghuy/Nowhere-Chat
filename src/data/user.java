package data;

import java.io.IOException;
import java.net.InetAddress;
import signup.SelectRecords;

public class user {
    private int user_id;
    private String user_name;
    private String name;
    private String gender;
    private String email;
    private String phone_number;
    private String ip_address;
    private int port;
    private Boolean status; // true is online, false is offline

    public user(int p) {
        try {
            InetAddress x = InetAddress.getLocalHost();
            this.ip_address = x.getHostAddress();
            this.port = p;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public user(int id, String username, String n, String gen, String eml, String phone, String ip, int p, boolean stat) {
        this.user_id = id;
        this.user_name = username;
        this.name = n;
        this.gender = gen;
        this.email = eml;
        this.phone_number = phone;
        this.ip_address = ip;
        this.port = p;
        this.status = stat;
    }

    public void updateIPandPort(int p) {
        try {
            InetAddress x = InetAddress.getLocalHost();
            this.ip_address = x.getHostAddress();
            this.port = p;
        } catch (IOException e) {
            e.printStackTrace();
        }
        SelectRecords sl = new SelectRecords();
        sl.updateIPandPort(this.user_name, this.ip_address, this.port);
    }

    public String getName() {
        return name;
    }

    public int getPort() {
        return port;
    }

    public String getIP() {
        return ip_address;
    }

    public int getUserID() {
        return user_id;
    }

    public boolean getStatus() {
        return status;
    }

    public String getUserName() {
        return user_name;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phone_number;
    }
}
