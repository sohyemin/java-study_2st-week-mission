package com.study.week2.mission2.server;

import com.study.week2.mission2.model.ChatMessage;
import com.study.week2.mission2.scheduler.MessageQueue;
import com.study.week2.mission2.session.SessionManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientHandler implements Runnable{
    private final Socket socket;
    private final SessionManager sessionManager;
    private final MessageQueue messageQueue;

    public ClientHandler(
            Socket socket,
            SessionManager sessionManager,
            MessageQueue messageQueue
    ) {
        this.socket = socket;
        this.sessionManager = sessionManager;
        this.messageQueue = messageQueue;
    }

    @Override
    public void run() {
        String userId = "user-" + socket.getPort();

        sessionManager.createSession(userId, socket);

        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream(),
                            StandardCharsets.UTF_8
                    )
            );

            String message;

            while ((message = reader.readLine()) != null) {
                if (message.equals("exit")) {
                    sessionManager.removeSession(userId);
                    socket.close();
                    break;
                }

                ChatMessage chatMessage =
                        new ChatMessage(userId, message);

                messageQueue.submit(chatMessage);
            }

        } catch (Exception e) {
            System.out.println("[ClientHandler] 종료 userId="
                    + userId
                    + ", reason="
                    + e.getMessage());

            sessionManager.removeSession(userId);
        }
    }

}
