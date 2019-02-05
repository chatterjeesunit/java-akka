package com.learn.akka.actors.helloakka;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class AckMessage {

    private String message;

}
