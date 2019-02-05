package com.learn.akka.actors.helloakka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class FirstAkkaDemo {


    public static void main(String[] args) {

        ActorSystem actorSystem = ActorSystem.create("demo");

        ActorRef actorOne = actorSystem.actorOf(ActorOne.props(), "actorOne");

        actorOne.tell(new DemoMessage("Hello World!!"), ActorRef.noSender());

//        actorSystem.stop(actorOne);

//        actorSystem.terminate();
    }

}
