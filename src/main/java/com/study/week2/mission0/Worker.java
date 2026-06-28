package com.study.week2.mission0;

import java.util.Random;

public class Worker implements Runnable {
    private Scheduler scheduler;
    private Random random = new Random();

    public Worker(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public void run() {
        while (true){
            try {
                Request request = scheduler.take();

                boolean success = callFakeAiApi(request);

                if (success){
                    System.out.println("[Worker] 성공 : " + request.getRequestId());
                } else {
                    System.out.println("[Worker] 실패 : " + request.getRequestId());
                    scheduler.retry(request);
                }
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
                System.out.println("[Worker] 종료");
                break;
            }
        }
    }

    private boolean callFakeAiApi(Request request) {
        // 외부 AI API 호출 시간을 흉내냄
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }

        // 70% 확률로 성공, 30% 확률로 실패
        return random.nextInt(100) < 70;
    }
}
