package io.yadnyesh.learnreactivespring.fluxandmonoplayground;

import org.junit.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;


public class ColdAndHotPublisherTest {

    @Test
    //For every new subscriber emits value from beginning
    public void coldPublisherTest() throws InterruptedException {
        Flux<String> stringFlux = Flux.just("A", "B", "C", "D", "E", "F")
                .delayElements(Duration.ofSeconds(1));
        stringFlux.subscribe(s -> System.out.println("Subscriber 1: " + s));
        Thread.sleep(2000);
        stringFlux.subscribe(s -> System.out.println("Subscriber 2: " + s));
        Thread.sleep(4000);
    }
}
