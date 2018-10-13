package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client_Socket {
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter your name: ");
            String name = sc.nextLine();
            Socket client = new Socket("localhost", 2234);
            PrintStream pr = new PrintStream(client.getOutputStream());
            while(true) {
                System.out.print("Enter your chat here: ");
                pr.println(name + ": " + sc.nextLine());
            }
        }
        catch (Exception e) {

        }
    }
}
