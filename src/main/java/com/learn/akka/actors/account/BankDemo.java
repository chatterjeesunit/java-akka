package com.learn.akka.actors.account;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BankDemo {

    private static final int NUM_SIZE = 5;

    public static void main(String[] args) throws InterruptedException {
        ActorSystem actorSystem = ActorSystem.create("bank-demo");


        ActorRef account = actorSystem.actorOf(BankAccount.props(1400), "accOne");

        ExecutorService executorService = Executors.newFixedThreadPool(NUM_SIZE);

        Set<Callable<Void>> callables = new HashSet<>();

        for (int i = 1; i <= NUM_SIZE; i++) {
            final WithdrawMessage msg = new WithdrawMessage(100 * i);
            Callable<Void> callable = () ->  {
                account.tell(msg, ActorRef.noSender());
                return null;
            };
            callables.add(callable);
        }

        executorService.invokeAll(callables);
        executorService.shutdown();
    }
}


