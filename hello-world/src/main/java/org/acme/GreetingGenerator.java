
package org.acme;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.enterprise.context.ApplicationScoped;

import io.reactivex.Flowable;

@ApplicationScoped
public class GreetingGenerator {

    String[] GREETINGS = new String[]{"Hi", "Ciao", "Ola", "Bonjour"};

    Random random = new Random();

    Flowable<String> greet() {
       return Flowable.interval(1, TimeUnit.SECONDS).map(t -> GREETINGS[random.nextInt(GREETINGS.length)]);
    }
}
