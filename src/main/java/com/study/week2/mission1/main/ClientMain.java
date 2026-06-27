package com.study.week2.mission1.main;

import com.study.week2.mission1.server.SocketClient;
import com.study.week2.mission1.server.SocketServer;

import java.io.IOException;

public class ClientMain {
    public static void main(String[] args) throws InterruptedException {

        int clientCount = 1000;

        for (int i = 0; i < 1000; i++) {
            Thread thread = new Thread(new SocketClient(i));
            thread.start();

            if(i%100==0){
                System.out.println(i + " clients started");
            }

            Thread.sleep(10);
        }
    }
}
