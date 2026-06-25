package com.study.week2.mission0;

import java.util.concurrent.PriorityBlockingQueue;

public class Scheduler {

    private PriorityBlockingQueue<Request> queue
            = new PriorityBlockingQueue<>(100, new PriorityCompare());


    public void submit(Request request){
        queue.add(request);
    }

    public Request take() throws InterruptedException {
        return queue.take();
    }

    public void retry(Request request){
        request.addRetryCount();

        long delay = calculateBackoff(request.getRetryCount());

        try{
            Thread.sleep(delay);
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
            return;
        }

        queue.add(request);
    }

    public long calculateBackoff(int retryCount){
        return (long) Math.pow(2, retryCount - 1) *1000;
    }
}
