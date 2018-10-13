package server;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server_Socket {
    private ServerSocket server;
    private JTextArea chat_window;

    static class receive_message extends Thread {
        private Socket socket;
        private JTextArea chat_area;

        public receive_message(Socket s, JTextArea ta) {
            this.socket = s;
            this.chat_area = ta;
        }

        public void run() {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while (true) {
                    String t = br.readLine();
                    chat_area.append(t);
                    System.out.println(t);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Server_Socket(JTextArea ta, int port) {
        this.chat_window = ta;

        try {
            System.out.println("waiting");
            server = new ServerSocket(port);
            acceptClient();
        }
        catch (Exception e) {

        }
    }

    public void acceptClient() {
        while (true) {
            try {
                System.out.println("waiting...");
                Socket s = server.accept();
                System.out.println("accepted");
                new receive_message(s, chat_window).start();
            }
            catch (Exception e) {

            }
        }
    }
}
