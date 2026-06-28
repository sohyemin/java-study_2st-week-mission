package com.study.week2.mission2.server;

import com.study.week2.mission2.factory.AIEngineFactory;
import com.study.week2.mission2.scheduler.MessageQueue;
import com.study.week2.mission2.session.SessionManager;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SocketServer {


    private final SessionManager sessionManager;
    private final MessageQueue messageQueue;

    private static final String END_SIGNAL = "[END]";

    public SocketServer(SessionManager sessionManager, MessageQueue messageQueue) {
        this.sessionManager = sessionManager;
        this.messageQueue = messageQueue;
    }

    public void start() throws IOException {

        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("서버 시작!");

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("클라이언트 연결!");

            new Thread(
                    new ClientHandler(socket, sessionManager, messageQueue)
            ).start();
        }
    }

}