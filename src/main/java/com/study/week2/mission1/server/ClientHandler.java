package com.study.week2.mission1.server;

import java.net.ServerSocket;
import java.net.Socket;

public class ClientHandler implements Runnable{

    private final ServerSocket serverSocket;

    public ClientHandler(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        try {

            Socket socket = serverSocket.accept();

            Thread.sleep(60_000);

            socket.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
