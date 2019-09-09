package com.learn.akka.actors.supervision;

import akka.actor.AbstractLoggingActor;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.japi.pf.DeciderBuilder;
import scala.Option;

import java.time.Duration;

public class CalculatorActor extends AbstractLoggingActor {

    private int counter = 0;

    static Props props() {
        return Props.create(CalculatorActor.class, CalculatorActor::new);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
            .match(ArithmeticMessage.class, msg -> {
                counter++;

                if(counter == 10) {
                    throw new RuntimeException("Max Limit reached!!!");
                }

                Thread.sleep(1);

                int op1 = Integer.parseInt(msg.getOperand1());
                int op2 = Integer.parseInt(msg.getOperand2());

                int result;
                switch (msg.getOperator()) {
                    case "+":
                        result = op1 + op2;
                        break;
                    case "-":
                        result = op1 - op2;
                        break;
                    case "*":
                        result = op1 * op2;
                        break;
                    case "/":
                        result = op1 / op2;
                        break;
                    default:
                        throw new IllegalArgumentException("Operator not supported : " + msg.getOperator());
                }
                log().info(counter + ": "+ msg + " = " + result);



            }).build();
    }


    @Override
    public void preStart() throws Exception {
        super.preStart();
        log().info("Pre Start.........");
    }

    @Override
    public void postStop() throws Exception {
        super.postStop();
        log().info("Post Stop.........");
    }

    @Override
    public void preRestart(Throwable reason, Option<Object> message) throws Exception {
        super.preRestart(reason, message);
        log().info("Pre Restart.........");
    }

    @Override
    public void postRestart(Throwable reason) throws Exception {
        super.postRestart(reason);
        log().info("Post Restart.........");
    }


}
