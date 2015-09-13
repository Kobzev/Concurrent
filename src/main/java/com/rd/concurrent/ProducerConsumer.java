package com.rd.concurrent;

/**
 * Created by Konstiantyn on 9/4/2015.
 */
public class ProducerConsumer {
    public static void main(String... args){
        Queue queue = new Queue();

        for (int i=0; i<4; i++){
            Producer1 producer = new Producer1(queue);
            Consumer1 consumer = new Consumer1(queue);

            producer.start();
            consumer.start();
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

class Producer1 extends Thread{
    private Queue queue;

    public Producer1(Queue queue){
        this.queue = queue;
    }

    @Override
    public void run() {
        int i=0;
        while (!isInterrupted()){
            System.out.println("Put" + "Message " + Thread.currentThread().getName() + " " + i);
            putMessage("Message " + Thread.currentThread().getName() + " " + i);
            i++;
        }
    }

    public void putMessage(String message){
        synchronized (queue){
            while (queue.setMessage(message) == false){
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}

class Consumer1 extends Thread{
    private Queue queue;

    public Consumer1(Queue queue){
        this.queue = queue;
    }

    @Override
    public void run() {
        while (!isInterrupted()){
            int i=0;
            getMessage();
            i++;
        }
    }

    public void getMessage(){
        String message;
        synchronized (queue){
            message = queue.getMessage();
            while (message == null){
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                message = queue.getMessage();
            }
        }
        System.out.println("Extract " + message);
    }
}

class Queue{
    private volatile String message;

    public Queue(){}

    public synchronized String getMessage() {
        if (message==null) return null;
        String temp = message;
        message = null;
        this.notifyAll();
        return temp;
    }

    public synchronized boolean setMessage(String massage) {
        if (message == null) {
            this.message = massage;
            this.notifyAll();
            return true;
        }
        return false;
    }
}