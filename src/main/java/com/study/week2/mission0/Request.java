package com.study.week2.mission0;

import java.time.LocalDateTime;

public class Request {
    private long requestId;
    private String userId;
    private String prompt;

    private int priority; // 우선도

    private int retryCount; // 재요청 횟수

    private LocalDateTime createdAt;

    public int getPriority() {
        return priority;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void addRetryCount() {
        this.retryCount++;
    }
}
