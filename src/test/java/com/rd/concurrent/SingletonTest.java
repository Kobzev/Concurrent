package com.rd.concurrent;

import org.junit.Test;

/**
 * Created by Konstiantyn on 9/4/2015.
 */
public class SingletonTest {

    @Test
    public void testSingleton(){
        ThreadPool threadPool = new ThreadPool(4);
        int num = 100;
        for (int i = 0; i<num; i++){
            threadPool.addTask(new Runnable() {
                public void run() {
                    System.out.println("" + Singleton.getInstance());
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        try {
            Thread.sleep(num*10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}