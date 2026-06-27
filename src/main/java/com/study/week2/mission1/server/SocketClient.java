package com.study.week2.mission1.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketClient implements Runnable{

    private final int clientId;

    public SocketClient(int clientId) {
        this.clientId = clientId;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket("localhost",8080);

            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println("hello from client = "+clientId);

            // 연결을 바로 끊지 않고 유지
            Thread.sleep(60_000);

            socket.close();
        } catch (IOException | InterruptedException e) {
            System.out.println("[Client-" + clientId + "] 종료: " + e.getMessage());
        }
    }
}