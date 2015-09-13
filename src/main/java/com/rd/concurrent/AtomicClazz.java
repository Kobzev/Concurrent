package com.rd.concurrent;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Konstiantyn on 9/9/2015.
 */
public class AtomicClazz {
    private volatile AtomicLong var;
    private volatile int degree;
    private volatile AtomicReference<BigInteger> result;
    static final BigInteger TWO = new BigInteger("2");

    public AtomicClazz() {
        var = new AtomicLong(1);
        degree = 0;
        result = new AtomicReference<>();
        result.set(BigInteger.ONE);
    }

    public BigInteger getNext(){
        for (;;){
            long current = var.get();
            long next = current * 2;
            if (var.compareAndSet(current, next)) return new BigInteger(String.valueOf(next));
        }
    }

    public BigInteger next(){
        for (;;){
            BigInteger current = result.get();
            BigInteger next = current.multiply(TWO);
            if (result.compareAndSet(current, next)) return next;
        }

    }

    public static void main(String... a){
        //Hashtable
        //HashMap
        String s1 = "string" , s2 = "string" ;
        System.out.println ( s1==s2 ? 1 : 0) ;
        String s3 = "str" , s4 = "ing" ;
        System.out.println( s1==(s3+s4) ? 1 : 0) ;
        System.out.println(s1 == ("str" + "ing") ? 1 : 0) ;
        String s5 = new String ( "string" ) ;
        System.out.println ( s1==s5 ? 1 : 0) ;
        System.out. println(s1.intern() == s5.intern() ? 1 : 0) ;
        String s6 = new String ( "string" ) ;
        System.out.println ( s1==s6.intern ( ) ? 1 : 0) ;
    }
}

class Super {
    public int field = 1;
    public int getField(){
        return field;
    }
}

class Sub extends Super {
    public int field = 5;
    public int getField(){
        return field;
    }
}