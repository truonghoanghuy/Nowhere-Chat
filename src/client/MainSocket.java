package client;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.TreeMap;
import gui.ChatPanel;
import data.*;

public class MainSocket extends SwingWorker {
    private ServerSocket server;
    private user usr;
    private int port;
    private TreeMap<String, ChatPanel> list_chat_sessions;
    private ArrayList<String> list_chat_conversations;
    private ArrayList<String> list_name_chat_conversations;

    public MainSocket(user usr, TreeMap<String, ChatPanel> list_chat_sessions, ArrayList<String> list_chat_conversations, ArrayList<String> list_name_chat_conversations) throws Exception {
        this.usr = usr;
        this.port = usr.getPort();
        this.list_chat_sessions = list_chat_sessions;
        this.list_chat_conversations = list_chat_conversations;
        this.list_name_chat_conversations = list_name_chat_conversations;
        this.server = new ServerSocket(port);
    }

    @Override
    protected Void doInBackground() throws Exception {
        while (true) {
            Socket client = server.accept();
            new chat_sessions(usr, client, list_chat_sessions, list_chat_conversations, list_name_chat_conversations).start();
            //update port
        }
    }

    static class chat_sessions extends Thread {
        private Socket client;
        private TreeMap<String, ChatPanel> list_chat_sessions;
        private ArrayList<String> list_chat_conversations;
        private ArrayList<String> list_name_chat_conversations;
        private ChatPanel chatpanel;
        private user usr;

        public chat_sessions(user u, Socket s, TreeMap<String, ChatPanel> list_chat_sessions, ArrayList<String> list_chat_conversations, ArrayList<String> list_name_chat_conversations) {
            this.usr = u;
            this.client = s;
            this.list_chat_conversations = list_chat_conversations;
            this.list_chat_sessions = list_chat_sessions;
            this.list_name_chat_conversations = list_name_chat_conversations;
        }

        public void run() {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String client_usrname = br.readLine();
                String client_name = br.readLine();
                if (!list_chat_sessions.containsKey(client_usrname)) {
                    list_chat_sessions.put(client_usrname, new ChatPanel(client_name, client, usr));
                    list_chat_conversations.add(client_usrname);
                    list_name_chat_conversations.add(client_name);
                }
                //chatpanel = list_chat_sessions.get(client_usrname);
            }
            catch (Exception e) {

            }
        }
    }
}
