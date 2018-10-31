package client;

import javax.swing.*;
import java.io.*;
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
    private int this_port;

    public MainSocket(user usr, TreeMap<String, ChatPanel> list_chat_sessions, ArrayList<String> list_chat_conversations, ArrayList<String> list_name_chat_conversations) throws Exception {
        this.usr = usr;
        this.port = usr.getPort();
        this.list_chat_sessions = list_chat_sessions;
        this.list_chat_conversations = list_chat_conversations;
        this.list_name_chat_conversations = list_name_chat_conversations;
        this.server = new ServerSocket(0);
    }

    public int getPort() {
        return this.server.getLocalPort();
    }

    @Override
    protected Void doInBackground() throws Exception {
        while (true) {
            Socket client = server.accept();
            new chat_sessions(usr, client, list_chat_sessions, list_chat_conversations, list_name_chat_conversations).start();
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
                ObjectOutputStream pr = new ObjectOutputStream(client.getOutputStream());
                ObjectInputStream br = new ObjectInputStream(client.getInputStream());
                ArrayList<String> lst = (ArrayList<String>)br.readObject();
                String client_usrname = lst.get(0);
                String client_name = lst.get(1);
                if (!list_chat_sessions.containsKey(client_usrname)) {
                    list_chat_sessions.put(client_usrname, new ChatPanel(client_name, client, usr, pr, br));
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
