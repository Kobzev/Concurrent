package com.rd.concurrent;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

import static org.junit.Assert.*;

/**
 * Created by Konstiantyn on 9/12/2015.
 */
public class BankSystemTest {

    @Test
    public void testTransfer() throws Exception {
        int numberOfAccounts = 1_000;//_000;

        List<Account> accounts = new ArrayList<>();

        int moneyInSystem = 0;

        for (int i=0; i<numberOfAccounts; i++) {
            accounts.add(new Account());
            accounts.get(i).add(5000);
            moneyInSystem+=accounts.get(i).getBalance();
        }

        ExecutorService service = new ThreadPoolExecutor(
                1000,
                2000,
                1000,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());

        for (int i = 0; i<5*numberOfAccounts; i++){
            service.execute(new Runnable() {
                @Override
                public void run() {
                    BankSystem.transfer(accounts.get(new Random().nextInt(numberOfAccounts)), accounts.get(new Random().nextInt(numberOfAccounts)), 200);
                }
            });
        }

        service.shutdown();


        int moneyInSystemAfterTransfer = 0;

        for (int i=0; i<numberOfAccounts; i++) {
            moneyInSystemAfterTransfer+=accounts.get(i).getBalance();
        }

        assertEquals(moneyInSystem, moneyInSystemAfterTransfer);

    }
}