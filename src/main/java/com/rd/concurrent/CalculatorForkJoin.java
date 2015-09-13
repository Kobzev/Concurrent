package com.rd.concurrent;

import java.util.concurrent.*;

/**
 * Created by Konstiantyn on 9/11/2015.
 */
public class CalculatorForkJoin {
    private ForkJoinPool executorService;

    public static void main(String ... args) throws ExecutionException, InterruptedException {
        System.out.println("finish = " + new CalculatorForkJoin().calculate(1000));
    }

    public CalculatorForkJoin() {
        executorService = new ForkJoinPool();
    }

    public Double calculate(int count) throws ExecutionException, InterruptedException {
        Future<Double> doubleFuture = executorService.submit(new FJCounter(0, count));
        //Double d = executorService.invoke(new FJCounter(0, count));
        executorService.shutdown();
        return doubleFuture.get();
        //return d;
    }
}

class FJCounter extends RecursiveTask<Double>{

    private int min;
    private int max;
    private final static int THRESHOLD = 5;

    public FJCounter(int min, int max) {
        this.min = min;
        this.max = max;
    }

    private int problemsize(){
        int problem = max - min;
        return problem;
    }

    private Double calc(){
        double result = 0.0;
        for (int iterator=min; iterator<max; iterator++){
            result += (Math.sin(Math.PI*iterator/180)*Math.sin(Math.PI*iterator/180) + Math.cos(Math.PI*iterator/180)*Math.cos(Math.PI*iterator/180));
        }
        return result;
    }

    @Override
    protected Double compute() {
        if (problemsize() < THRESHOLD){
            return calc();
        }

        int middle = (max + min) / 2;

        FJCounter left = new FJCounter(min, middle);
        FJCounter right = new FJCounter(middle, max);

        right.fork();

        return left.compute() + right.join();
    }
}
