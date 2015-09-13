package com.rd.concurrent;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Created by Konstiantyn on 9/11/2015.
 */
public class CalculateSumThreadPoolTest {

    @Test
    public void testCalculateSum() throws Exception {
        CalculatorConcurent calculatorConcurent = new CalculatorConcurent(5);
        Double result = calculatorConcurent.count(1000);

        double expected = 0.0;
        for (int i=0; i<1000; i++) {expected++;}

        System.out.println(expected);

        assertEquals(result, expected, 0.01);
    }
}
