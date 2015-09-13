package com.rd.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by Konstiantyn on 9/11/2015.
 */
public class CalculatorConcurent {

    private ExecutorService executor;

    public CalculatorConcurent(int count) {
        //executor = Executors.newFixedThreadPool(count);
        executor = new ForkJoinPool();
    }

    public Double count(int count) throws InterruptedException, ExecutionException {
        List<Callable<Double>> tasks = new ArrayList<>();

        for (int i = 0; i < count; i++){
            tasks.add(new Task(i, i+1));
        }

        List<Future<Double>> results = executor.invokeAll(tasks);

        double result = 0.0;

        for (Future<Double> res : results){
            result += res.get();
        }

        executor.shutdown();

        return result;
    }

}

class Task implements Callable<Double>{

    private int min;
    private int max;

    public Task(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public Double call() throws Exception {
        double result = 0.0;
        for (int iterator=min; iterator<max; iterator++){
            result += Math.sin(Math.PI*iterator/180)*Math.sin(Math.PI*iterator/180) + Math.cos(Math.PI*iterator/180)*Math.cos(Math.PI*iterator/180);
        }
        return result;
    }
}
