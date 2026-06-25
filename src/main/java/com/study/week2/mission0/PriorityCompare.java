package com.study.week2.mission0;

import java.util.Comparator;

public class PriorityCompare implements Comparator<Request> {
    @Override
    public int compare(Request request1, Request request2) {
        if (request1.getPriority() == request2.getPriority()) {
            return request1.getCreatedAt().compareTo(request2.getCreatedAt());
        } else {
            return Integer.compare( request2.getPriority(), request1.getPriority());
        }
    }
}