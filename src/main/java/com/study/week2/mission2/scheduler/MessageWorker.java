package com.study.week2.mission2.scheduler;


import com.study.week2.mission2.engine.AIEngine;
import com.study.week2.mission2.log.ChatLogWriter;
import com.study.week2.mission2.model.ChatMessage;
import com.study.week2.mission2.server.BroadcastManager;

public class MessageWorker implements Runnable {
    private final MessageQueue messageQueue;
    private final AIEngine engine;
    private final BroadcastManager broadcastManager;
    private final ChatLogWriter logWriter;

    public MessageWorker(MessageQueue messageQueue, AIEngine engine, BroadcastManager broadcastManager, ChatLogWriter logWriter) {
        this.messageQueue = messageQueue;
        this.engine = engine;
        this.broadcastManager = broadcastManager;
        this.logWriter = logWriter;
    }

    @Override
    public void run() {
        while (true){
            try{
                ChatMessage chatMessage = messageQueue.take();

                String aiMessage = engine.chat(chatMessage.getPrompt());

                broadcastManager.broadcast(aiMessage);

                logWriter.write(aiMessage);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("[MessageWorker] 종료");
                break;
            }
        }
    }
}

