package com.learn.akka.actors.supervision;

import akka.actor.*;
import akka.japi.pf.DeciderBuilder;

import java.time.Duration;

public class MainSupervisorActor extends AbstractLoggingActor {

    private MainSupervisorActor() {
        ActorRef child = getContext().actorOf(SupervisorActor.props(), "supervisor");
        log().info("Created child : " + child.path().toString());
    }
    static Props props() {
        return Props.create(MainSupervisorActor.class, MainSupervisorActor::new);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchAny(m ->
                    getContext()
                    .getSystem()
                    .actorSelection("akka://calculator-demo/user/main/supervisor")
                    .tell(m, self())
                ).build();
    }


    @Override
    public SupervisorStrategy supervisorStrategy() {
        return new OneForOneStrategy(
                5,
                Duration.ofMinutes(1),
                DeciderBuilder
                        .matchAny(e -> SupervisorStrategy.resume())
                        .build()
        );
    }
}
