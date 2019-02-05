package com.learn.akka.actors.supervision;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class SupervisorDemo {

    public static void main(String[] args) throws IOException {
        ActorSystem actorSystem = ActorSystem.create("calculator-demo");

        ActorRef supervisor = actorSystem.actorOf(MainSupervisorActor.props(), "main");

        readLines().forEach( line -> supervisor.tell(line, supervisor));
    }

    private static List<ArithmeticMessage> readLines() throws IOException {
        InputStream inputStream = SupervisorDemo.class.getClassLoader().getResourceAsStream("input.txt");

        return IOUtils.readLines(inputStream, StandardCharsets.UTF_8).stream().map(str -> {
            String[] values = str.split(",");
            return new ArithmeticMessage(values[0], values[1], values[2]);
        }).collect(Collectors.toList());

    }
}
