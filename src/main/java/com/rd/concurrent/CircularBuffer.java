package com.rd.concurrent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Konstiantyn on 9/9/2015.
 */
public class CircularBuffer<T> {
    private final T[] queue;
    private volatile int head;
    private volatile int tail;

    public CircularBuffer(int length) {
        queue = (T[])new Object[length];
        head = 0;
        tail = 0;
    }

    public synchronized T getMessage() throws InterruptedException {
        T value = null;
        while (value == null) {
            if (head != tail) {
                value = queue[head++];
                if (head == queue.length) {
                    head = 0;
                }
            } else {
                this.wait();
            }
        }
        this.notifyAll();
        return value;
    }

    public synchronized void addMessage(T message) throws InterruptedException {
        if (!bufferFull()) {
            queue[tail++] = message;
            if (tail == queue.length) {
                tail = 0;
            }
        }else {this.wait();}
        this.notifyAll();
    }

    public boolean bufferFull() {
        if (tail + 1 == head) {
            return true;
        }
        if (tail == (queue.length - 1) && head == 0) {
            return true;
        }
        return false;
    }

    public static void main(String ... args){
        CircularBuffer<String> queue = new CircularBuffer<>(10);
        List<Thread> threads = new ArrayList<>();

        for (int i=0; i<15; i++){
            Producer<String> producer = new Producer(queue);
            Consumer<String> consumer = new Consumer(queue);

            threads.add(producer);
            threads.add(consumer);

            producer.start();
            consumer.start();
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Thread thread : threads){thread.interrupt();}
    }
}

class Producer<T> extends Thread{
    private CircularBuffer<T> queue;

    public Producer(CircularBuffer<T> queue){
        this.queue = queue;
    }

    @Override
    public void run() {
        int i=0;
        while (!isInterrupted()){
            System.out.println("Put " + "Message " + Thread.currentThread().getName() + " " + i);
            try {
                queue.addMessage((T) ((T)"Message " + Thread.currentThread().getName() + " " + i));
            }catch (InterruptedException e){return;}
            i++;
        }
    }
}

class Consumer<T> extends Thread{
    private CircularBuffer<T> queue;

    public Consumer(CircularBuffer<T> queue){
        this.queue = queue;
    }

    @Override
    public void run() {
        while (!isInterrupted()){
            T message;
            try {
                message = queue.getMessage();
            }catch (InterruptedException e){return; }
            System.out.println("Extract " + message);
        }
    }
}
