package com.rd.concurrent;

import java.util.Random;

/**
 * Created by Konstiantyn on 9/9/2015.
 */
public class Philosophers {
    public static final int NUMBER_OF_PHILOSOPHERS = 5;

    public static void main(String ... args) throws InterruptedException {
        Fork[] forks = new Fork[NUMBER_OF_PHILOSOPHERS];
        for (int i=0; i<NUMBER_OF_PHILOSOPHERS; i++){
            forks[i] = new Fork(i);
        }

        Philosopher[] philosophers = new Philosopher[NUMBER_OF_PHILOSOPHERS];
        for (int i=0; i<NUMBER_OF_PHILOSOPHERS; i++){
            Fork leftFork = forks[i];
            Fork rightFork = forks[(i+1)%NUMBER_OF_PHILOSOPHERS];
            philosophers[i] = new Philosopher(leftFork, rightFork, i);
            philosophers[i].start();
        }

        Thread.sleep(1_000_00);

        for(int i=0; i<NUMBER_OF_PHILOSOPHERS; i++){
            philosophers[i].interrupt();
        }

        for(int i=0; i<NUMBER_OF_PHILOSOPHERS; i++){
            philosophers[i].join();
            System.out.println("Philosopher #"+i+" eats " + philosophers[i].getEatTime() + " times");
        }
    }

}

class Fork{
    private boolean free = true;
    private final int order;

    public Fork(int order) {
        this.order = order;
    }

    public synchronized void take() throws InterruptedException {
        while (!free) {
            this.wait();
        }
        free = false;
    }

    public synchronized void release(){
        free = true;
        this.notifyAll();
    }

    public int getOrder() {
        return order;
    }
}

class Philosopher extends Thread{
    private final Fork leftFork;
    private final Fork rightFork;
    private int order;
    private int eatTime;

    public Philosopher(Fork leftFork, Fork rightFork, int order) {
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.order = order;
    }

    public int getEatTime() {
        return eatTime;
    }

    @Override
    public void run() {
        while (!isInterrupted()){
            Fork fork1 = null;
            Fork fork2 = null;
            try{
                if (leftFork.getOrder() > rightFork.getOrder()){
                    fork1 = leftFork;
                    fork2 = rightFork;
                }else{
                    fork1 = rightFork;
                    fork2 = leftFork;
                }

                System.out.println("Philosopher #" + order + " trying to take first fork ");
                fork1.take();
                System.out.println("Philosopher #" + order + " trying to take second fork ");
                fork2.take();
                System.out.println("Philosopher #" + order + " eating ");
                Thread.sleep(25);//new Random().nextInt(100));
                System.out.println("Philosopher #" + order + " stop eating ");
                eatTime++;
                fork2.release();
                fork1.release();
                System.out.println("Philosopher #" + order + " thinking");
                Thread.sleep(25);//new Random().nextInt(100));
            }catch (InterruptedException e){return;}
        }
    }
}
