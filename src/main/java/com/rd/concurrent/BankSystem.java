package com.rd.concurrent;

/**
 * Created by Konstiantyn on 9/9/2015.
 */
public class BankSystem {

    public static boolean transfer(Account accountFrom, Account accountTo, int money){
        if (money<=0) {System.out.println("wrong sum"); return false;}
        if (accountFrom.getNumber().compareTo(accountTo.getNumber())>0){
            synchronized (accountFrom){
                synchronized (accountTo){
                    return transferMoney(accountFrom, accountTo, money);
                }
            }
        }else{
            synchronized (accountTo){
                synchronized (accountFrom){
                    return transferMoney(accountFrom, accountTo, money);
                }
            }
        }
    }

    private static boolean transferMoney(Account accountFrom, Account accountTo, int money){
        if (!accountFrom.withdraw(money)) {System.out.println("not enough money"); return false;}
        if (!accountTo.add(money)) {accountFrom.add(money); return false;}
        return true;
    }
}

class Account{
    public static int lastAccount = 0;
    private Integer number;
    private int balance;

    public Account() {
        number = ++lastAccount;
    }

    public Integer getNumber() {
        return number;
    }

    public float getBalance() {
        return balance;
    }

    public boolean withdraw(int money){
        if (money > balance || money <= 0) return false;

        balance-=money;

        return true;
    }

    public boolean add(int money){
        if (money <= 0) return false;
        balance+=money;
        return true;
    }
}
