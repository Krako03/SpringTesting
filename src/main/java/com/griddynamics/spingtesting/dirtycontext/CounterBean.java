package com.griddynamics.spingtesting.dirtycontext;

import org.springframework.stereotype.Component;

@Component
public class CounterBean {
    private int count = 0;

    public CounterBean() {
        System.out.println("CounterBean created");
    }

    public int getCount() {
        return count;
    }

    public void increment() {
        count++;
    }

    public void reset() {
        count = 0;
    }
}