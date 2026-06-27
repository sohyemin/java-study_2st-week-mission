package com.study.week2.mission1.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer implements Runnable{

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            System.out.println("서버 시작!");

            while (true){
                Socket socket = serverSocket.accept();
                System.out.println("클라이언트 연결!");

                new Thread(new ClientHandler(serverSocket)).start();
            }

        } catch (IOException e){
            System.out.println("서버 종료 : " + e);
        }

    }
}