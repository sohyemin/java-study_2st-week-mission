package com.study.week2.mission2.main;

import com.study.week2.mission2.server.SocketClient;

public class ClientMain {

    public static void main(String[] args) throws Exception {

        SocketClient client = new SocketClient();

        client.connect();
    }
}
