package com.learn.akka.actors.supervision;

import akka.actor.*;
import akka.japi.pf.DeciderBuilder;

import java.time.Duration;

public class SupervisorActor extends AbstractLoggingActor {

    private SupervisorActor() {
        ActorRef child = getContext().actorOf(CalculatorActor.props(), "calculator");
        log().info("Created child : " + child.path().toString());
    }
    static Props props() {
        return Props.create(SupervisorActor.class, SupervisorActor::new);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchAny(m ->
                    getContext()
                    .getSystem()
                    .actorSelection("akka://calculator-demo/user/main/supervisor/calculator")
                    .tell(m, self())
                ).build();
    }


    @Override
    public SupervisorStrategy supervisorStrategy() {
        return new OneForOneStrategy(
                5,
                Duration.ofMinutes(1),
                DeciderBuilder
                        .match(NumberFormatException.class, e -> SupervisorStrategy.resume())
                        .match(ArithmeticException.class, e -> SupervisorStrategy.stop())
                        .match(IllegalArgumentException.class, e -> SupervisorStrategy.escalate())
                        .match(RuntimeException.class, e -> SupervisorStrategy.restart())
                        .build()
        );
    }
}
