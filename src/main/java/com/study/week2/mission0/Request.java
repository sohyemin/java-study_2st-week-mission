package com.study.week2.mission0;

import java.time.LocalDateTime;

public class Request {
    long requestId;
    String userId;
    String prompt;

    int priority; // 우선도

    int retryCount; // 재요청 횟수

    LocalDateTime createdAt;
}
