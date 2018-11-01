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
    private TreeMap<String, ChatPanel> list_chat_sessions;
    private ArrayList<user> list_recent_chats;

    public MainSocket(user usr, TreeMap<String, ChatPanel> list_chat_sessions, ArrayList<user> list_chat_conversations) throws Exception {
        this.usr = usr;
        this.list_chat_sessions = list_chat_sessions;
        this.list_recent_chats = list_chat_conversations;
        this.server = new ServerSocket(0);
    }

    public int getPort() {
        return this.server.getLocalPort();
    }

    @Override
    protected Void doInBackground() throws Exception {
        while (true) {
            Socket client = server.accept();
            new chat_sessions(usr, client, list_chat_sessions, list_recent_chats).start();

        }
    }

    static class chat_sessions extends Thread {
        private Socket client;
        private TreeMap<String, ChatPanel> list_chat_sessions;
        private ArrayList<user> list_recent_chats;
        private user usr;

        public chat_sessions(user u, Socket s, TreeMap<String, ChatPanel> list_chat_sessions, ArrayList<user> list_chat_conversations) {
            this.usr = u;
            this.client = s;
            this.list_recent_chats = list_chat_conversations;
            this.list_chat_sessions = list_chat_sessions;
        }

        public void run() {
            try {
                ObjectOutputStream pr = new ObjectOutputStream(client.getOutputStream());
                ObjectInputStream br = new ObjectInputStream(client.getInputStream());
                user u = (user) br.readObject();
                if (!list_chat_sessions.containsKey(u.getUser_name())) {
                    list_chat_sessions.put(u.getUser_name(), new ChatPanel(u.getName(), client, usr, pr, br));
                    list_recent_chats.add(u);
                }
            }
            catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error at creating new chat session!");
            }
        }
    }
}
