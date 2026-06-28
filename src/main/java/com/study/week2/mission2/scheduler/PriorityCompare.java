package com.study.week2.mission2.scheduler;

import com.study.week2.mission2.model.ChatMessage;

import java.util.Comparator;

public class PriorityCompare implements Comparator<ChatMessage> {
    @Override
    public int compare(ChatMessage message1, ChatMessage message2) {
        if (message1.getPriority() == message2.getPriority()) {
            return message1.getCreatedAt().compareTo(message2.getCreatedAt());
        } else {
            return Integer.compare(message2.getPriority(), message1.getPriority());
        }
    }
}