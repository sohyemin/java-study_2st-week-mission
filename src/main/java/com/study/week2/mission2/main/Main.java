package com.study.week2.mission2.main;


import com.study.week2.mission2.server.SocketServer;

public class Main {
    public static void main(String[] args) throws Exception{

        SocketServer server = new SocketServer();

        server.start();
    }
}