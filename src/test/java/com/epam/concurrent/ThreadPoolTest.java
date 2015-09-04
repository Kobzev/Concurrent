package com.epam.concurrent;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by Konstiantyn on 9/2/2015.
 */
public class ThreadPoolTest {

    @Test
    public void testNormalWork() throws InterruptedException {

        ThreadPool threadPool = new ThreadPool(4);
        threadPool.start();

        int num = 1000000;

        Thread.sleep(1000);
        final AtomicInteger count = new AtomicInteger(0);
        for (int i = 0; i<num; i++){
            final int coun = i;
            threadPool.addTask(new Runnable() {
                public void run() {
                    System.out.println("" + coun + Thread.currentThread().getName());
                    count.addAndGet(coun);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        Thread.sleep(num*10);
        //threadPool.stop();

        int expected = 0;
        for (int i=0; i<num; i++){expected +=i;}

        assertEquals(expected, count.get());
    }
}