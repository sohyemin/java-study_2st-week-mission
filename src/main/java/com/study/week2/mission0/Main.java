package com.study.week2.mission0;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {

        ExecutorService service = Executors.newFixedThreadPool(5);
        Scheduler scheduler = new Scheduler();

        Request request1 = new Request(1L, "user1", "ㅁㅅㅁ", 10);
        Request request2 = new Request(2L, "user2", "ㅁㅅㅁ1", 3);
        Request request3 = new Request(3L, "user3", "ㅁㅅㅁ2", 5);
        Request request4 = new Request(4L, "user4", "ㅁㅅㅁ3", 10);

        scheduler.submit(request1);
        scheduler.submit(request2);
        scheduler.submit(request3);
        scheduler.submit(request4);

        service.submit(new Worker(scheduler));
        service.submit(new Worker(scheduler));
        service.submit(new Worker(scheduler));
        service.submit(new Worker(scheduler));


    }
}
