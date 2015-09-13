package com.rd.concurrent;

import java.util.LinkedList;

/**
 * Created by Konstiantyn on 9/2/2015.
 */
public class ThreadPool {

    private final int countThread;
    private final Worker[] pool;
    private final LinkedList<Runnable> tasks;

    public ThreadPool(int countThread) {
        this.countThread = countThread;
        pool = new Worker[countThread];
        tasks = new LinkedList<Runnable>();
    }

    public void start(){
        for (int i=0; i<countThread; i++){
            pool[i] = new Worker("Theads from pool-"+i);
            pool[i].start();
        }
    }

    public void addTask(Runnable task){
        synchronized (tasks){
            tasks.addLast(task);
            tasks.notify();
        }

    }

    public void stop(){
        for (int i=0; i<countThread; i++){
            pool[i].interrupt();
        }
    }

    class Worker extends Thread{

        public Worker(String name){
            super(name);
        }

        @Override
        public void run() {
            Runnable r = null;
            while (!isInterrupted()){
                synchronized(tasks) {
                    while (tasks.isEmpty()) {
                        try{
                            tasks.wait();
                        } catch (InterruptedException ignored){ return; }
                    }
                    r = tasks.removeFirst();
                    //tasks.notify();
                }

                if (r != null) {
                    try{
                        r.run();
                    }catch (Exception e){e.printStackTrace();}
                }
            }
        }
    }
}
