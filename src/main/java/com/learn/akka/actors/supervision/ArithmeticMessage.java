package com.learn.akka.actors.supervision;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArithmeticMessage {
    private String operator;
    private String operand1;
    private String operand2;

    @Override
    public String toString(){
        return operand1 + " " + operator + " " + operand2;
    }

}
