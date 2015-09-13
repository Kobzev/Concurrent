package com.rd.concurrent;

/**
 * Created by Konstiantyn on 9/4/2015.
 */
public class Singleton {
    private Singleton(){}

    private static volatile Singleton instance;

    public static Singleton getInstance(){
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) instance = new Singleton();
            }
        }
        return instance;
    }
}
