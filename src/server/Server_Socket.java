package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server_Socket {
    static class receive_message extends Thread {
        private Socket socket = null;
        public receive_message(Socket s) {
            this.socket = s;
        }
        public void run() {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while (true) {
                    System.out.println(br.readLine());
                }
            }
            catch (Exception e) {

            }
        }
    }
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(2234);
            while (true) {
                Socket s = server.accept();
                new receive_message(s).start();
            }
        }
        catch(Exception e) {

        }
    }
}
