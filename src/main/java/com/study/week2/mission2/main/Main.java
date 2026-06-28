package com.study.week2.mission2.main;


import com.study.week2.mission2.engine.AIEngine;
import com.study.week2.mission2.engine.OllamaEngine;
import com.study.week2.mission2.log.ChatLogWriter;
import com.study.week2.mission2.scheduler.MessageQueue;
import com.study.week2.mission2.scheduler.MessageWorker;
import com.study.week2.mission2.server.BroadcastManager;
import com.study.week2.mission2.server.SocketServer;
import com.study.week2.mission2.session.SessionManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws Exception{

        SessionManager sessionManager = new SessionManager();
        MessageQueue messageQueue = new MessageQueue();

        AIEngine aiEngine = new OllamaEngine();
        ChatLogWriter logWriter = new ChatLogWriter("chat.log");

        BroadcastManager broadcastManager =
                new BroadcastManager(sessionManager);

        ExecutorService workerPool = Executors.newFixedThreadPool(3);

        for (int i = 0; i < 3; i++) {
            workerPool.submit(
                    new MessageWorker(
                            messageQueue,
                            aiEngine,
                            broadcastManager,
                            logWriter
                    )
            );
        }

        SocketServer server =
                new SocketServer(sessionManager, messageQueue);

        server.start();
    }
}