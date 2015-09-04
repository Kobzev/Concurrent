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

        //Thread.sleep(1000);
        final AtomicInteger count = new AtomicInteger(0);
        for (int i = 0; i<1000; i++){
            final int coun = i;
            threadPool.addTask(new Runnable() {
                public void run() {
                    /*try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/

                    //System.out.println(coun);
                    count.addAndGet(coun);
                }
            });
        }

        Thread.sleep(1000);
        threadPool.stop();

        int expected = 0;
        for (int i=0; i<1000; i++){expected +=i;}

        assertEquals(expected, count.get());
    }
}