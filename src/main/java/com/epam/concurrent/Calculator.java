package com.epam.concurrent;

/**
 * Created by Konstiantyn on 9/2/2015.
 */
public class Calculator {

    public double calculateSum(int countThread, final int maxValue){
        Function[] arrayThread = new Function[countThread];
        double sum = 0;
        int step = maxValue/countThread;
        for (int i=0; i<countThread; i++){
            final int min = step*i;
            int maxVal = 0;
            if (i==countThread-1) {maxVal = maxValue;}
            else {maxVal = step*(i+1);}
            final int max = maxVal;

            Function thread = new Function(min, max);
            arrayThread[i] = thread;
            thread.start();
        }

        for (int i=0; i<countThread; i++) {
            try {
                arrayThread[i].join();
                sum +=arrayThread[i].getResult();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return sum;
    }

    class Function extends Thread{
        int min;
        int max;
        double result;

        public Function(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public double getResult(){
            return result;
        }

        @Override
        public void run() {
            for (int iterator=min; iterator<max; iterator++){
                result += Math.sin(Math.PI*iterator/180)*Math.sin(Math.PI*iterator/180) + Math.cos(Math.PI*iterator/180)*Math.cos(Math.PI*iterator/180);
            }
        }
    }
}
