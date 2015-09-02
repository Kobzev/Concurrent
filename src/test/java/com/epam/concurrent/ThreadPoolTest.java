package com.epam.concurrent;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Konstiantyn on 9/2/2015.
 */
public class ThreadPoolTest {

    @Test
    public void testNormalWork() throws InterruptedException {

        ThreadPool threadPool = new ThreadPool(4);

        Thread.sleep(1000);

        for (int i = 0; i<1000; i++){
            final int count = i;
            threadPool.addTask(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println(count);
                }
            });
        }

        Thread.sleep(10000);

    }
}