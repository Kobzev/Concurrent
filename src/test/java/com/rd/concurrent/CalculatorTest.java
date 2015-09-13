package com.rd.concurrent;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Konstiantyn on 9/2/2015.
 */
public class CalculatorTest {

    @Test
    public void testCalculateSum() throws Exception {
        Calculator calculateRange = new Calculator();
        int maxValue = 180;
        double sum = calculateRange.calculateSum(4, maxValue);

        double expectValue = 0;
        for (int i=0; i<maxValue; i++ ){
            expectValue += Math.sin(Math.PI*i/180)*Math.sin(Math.PI*i/180) + Math.cos(Math.PI*i/180)*Math.cos(Math.PI*i/180);
        }
        assertEquals(sum, expectValue, 0.01);
    }
}