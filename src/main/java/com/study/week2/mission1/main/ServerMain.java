package com.study.week2.mission1.main;

import com.study.week2.mission1.server.SocketServer;

import java.io.IOException;

public class ServerMain {
    public static void main(String[] args) throws IOException {
        Thread thread = new Thread(new SocketServer());

        thread.start();
    }
}
