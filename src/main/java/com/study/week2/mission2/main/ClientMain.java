package com.study.week2.mission2.main;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientMain {
    public static void main(String[] args) throws InterruptedException {
        int clientCount = 3;

        for (int i = 1; i <= clientCount; i++) {
            int clientId = i;

            new Thread(() -> runClient(clientId)).start();

            Thread.sleep(500);
        }
    }

    private static void runClient(int clientId) {
        try (
                Socket socket = new Socket("localhost", 8080);
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8)
                );
                PrintWriter writer = new PrintWriter(
                        new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8),
                        true
                )
        ) {
            writer.println("안녕, 나는 client-" + clientId + "야.");

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("[Client-" + clientId + " 수신] " + line);
            }

        } catch (IOException e) {
            System.out.println("[Client-" + clientId + "] 종료: " + e.getMessage());
        }
    }
}
