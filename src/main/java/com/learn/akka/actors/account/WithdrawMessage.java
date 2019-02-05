package com.learn.akka.actors.account;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WithdrawMessage {
    private int amountToWithdraw;
}
