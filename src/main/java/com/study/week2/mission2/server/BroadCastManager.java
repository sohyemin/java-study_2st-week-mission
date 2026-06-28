package com.study.week2.mission2.server;

import com.study.week2.mission2.session.ChatSession;
import com.study.week2.mission2.session.SessionManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class BroadCastManager {
    private final SessionManager manager;

    public BroadCastManager(SessionManager manager) {
        this.manager = manager;
    }

    public void broadcast(String message){
        for(ChatSession session : manager.getAllSession()){
            send(session, message);
        }
    }

    private void send(ChatSession session, String message) {
        Socket socket = session.getSocket();

        try {
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println("[AI] " + message);
        } catch (IOException e) {
            System.out.println("[BroadcastManager] 전송 실패 userId="
                    + session.getUserId()
                    + ", reason="
                    + e.getMessage());

            manager.removeSession(session.getUserId());
        }
    }
}
