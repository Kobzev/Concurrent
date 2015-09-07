package com.epam.concurrent;

/**
 * Created by Konstiantyn on 9/4/2015.
 */
public class ProducerConcumer {
    public static void main(String... args){
        Queue queue = new Queue();

        for (int i=0; i<4; i++){
            Producer producer = new Producer(queue);
            Consumer consumer = new Consumer(queue);

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

class Producer extends Thread{
    private Queue queue;

    public Producer(Queue queue){
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

class Consumer extends Thread{
    private Queue queue;

    public Consumer(Queue queue){
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