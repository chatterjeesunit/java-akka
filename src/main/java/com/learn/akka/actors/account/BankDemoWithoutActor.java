package com.learn.akka.actors.account;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BankDemoWithoutActor {

    private static final int NUM_SIZE = 5;

    public static void main(String[] args) throws InterruptedException {

        UnSafeBankAccount unSafeBankAccount = new UnSafeBankAccount(1400);

        ExecutorService executorService = Executors.newFixedThreadPool(NUM_SIZE);

        Set<Callable<Void>> callables = new HashSet<>();

        for (int i = 1; i <= NUM_SIZE; i++) {
            final int withdrawAmount = 100 * i;
            Callable<Void> callable = () ->  {
                unSafeBankAccount.withdraw(withdrawAmount);
                return null;
            };
            callables.add(callable);
        }

        executorService.invokeAll(callables);
        executorService.shutdown();
    }
}


@Data
@AllArgsConstructor
class UnSafeBankAccount {

    private int amount;

    public void withdraw(int amountToWithdraw) {
        if(amount >= amountToWithdraw) {
            amount = amount - amountToWithdraw;
            System.out.println("Withdrew " + amountToWithdraw + " from account. Final Balance is : " + amount);
        }else {
            System.out.println("Withdrawing " + amountToWithdraw + " from account. Not sufficient amount - " + amount + ". Withdraw not allowed.");
        }
    }
}