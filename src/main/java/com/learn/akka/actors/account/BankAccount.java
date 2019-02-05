package com.learn.akka.actors.account;

import akka.actor.AbstractLoggingActor;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;

public class BankAccount extends AbstractLoggingActor {

    private int amount;

    public BankAccount(int amount) {
        this.amount = amount;
    }

    static Props props(int amount) {
        return Props.create(BankAccount.class, amount);
    }

    @Override
    public Receive createReceive() {
        return ReceiveBuilder
                .create()
                .match(WithdrawMessage.class, msg -> {
                    if(amount >= msg.getAmountToWithdraw()) {
                        amount -= msg.getAmountToWithdraw();
                        log().info("Withdrew {} from account. Final Balance is : {}", msg.getAmountToWithdraw(), amount);
                    }else {
                        log().info("Withdrawing {} from account. Not sufficient amount - {}. Withdraw not allowed.", msg.getAmountToWithdraw(), amount);
                    }

                }).build();
    }
}

