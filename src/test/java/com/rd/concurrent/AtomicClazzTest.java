package com.rd.concurrent;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Created by Konstiantyn on 9/9/2015.
 */
public class AtomicClazzTest {

    @Test
    public void testGetNext() throws Exception {
        ThreadPool threadPool = new ThreadPool(10);
        threadPool.start();

        final AtomicClazz atomicClazz = new AtomicClazz();
        for (int i = 0; i<63; i++){
            threadPool.addTask(new Runnable() {
                public void run() {
                    System.out.println("" + atomicClazz.getNext() + Thread.currentThread().getName());
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        Thread.sleep(65*100);

        System.out.println("--------------------------------------------------------------");

        AtomicClazz atomicClazz1 = new AtomicClazz();
        for (int i = 0; i<63; i++){
            System.out.println("" + atomicClazz1.getNext());
        }
    }

    @Test
    public void testNext() throws Exception {
        ThreadPool threadPool = new ThreadPool(10);
        threadPool.start();

        final AtomicClazz atomicClazz = new AtomicClazz();
        for (int i = 0; i<150; i++){
            threadPool.addTask(new Runnable() {
                public void run() {
                    System.out.println("" + atomicClazz.next() + Thread.currentThread().getName());
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        Thread.sleep(65*100);

        System.out.println("--------------------------------------------------------------");

        AtomicClazz atomicClazz1 = new AtomicClazz();
        for (int i = 0; i<150; i++){
            System.out.println("" + atomicClazz1.next());
        }
    }

    @Test
    public void testExecutor() throws Exception {
        ExecutorService executorService = new ThreadPoolExecutor(
        /*int corePoolSize,
        int maximumPoolSize,
        long keepAliveTime,
        TimeUnit unit,
        BlockingQueue<Runnable> workQueue,
        RejectedExecutionHandler handler*/
                5,10,100L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(30), new ThreadPoolExecutor.CallerRunsPolicy());
    }
}