package data;

import java.io.Serializable;

public class user implements Serializable {
    private int user_id;
    private String user_name;
    private String name;
    private String gender;
    private String email;
    private String phone_number;
    private String ip_address;
    private int port;
    private Boolean status; // true is online, false is offline

    public user(int user_id, String user_name, String name, String gender, String email, String phone_number, String ip, int port, boolean status) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.phone_number = phone_number;
        this.ip_address = ip;
        this.port = port;
        this.status = status;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getIp_address() {
        return ip_address;
    }

    public int getPort() {
        return port;
    }

    public Boolean getStatus() {
        return status;
    }
}
