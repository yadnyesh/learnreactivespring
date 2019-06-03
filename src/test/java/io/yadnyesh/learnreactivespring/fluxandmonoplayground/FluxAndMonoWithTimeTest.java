package io.yadnyesh.learnreactivespring.fluxandmonoplayground;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class FluxAndMonoWithTimeTest {

    @Test
    public void FluxAndMonoTimeTest() throws InterruptedException {
        Flux<Long> infiniteFlux = Flux.interval(Duration.ofMillis(200))
                .log();
        infiniteFlux.subscribe((e) -> System.out.println("Current Element is: " + e));

        Thread.sleep(3000);
    }

    @Test
    public void InfiniteSequenceTest() throws InterruptedException {
        Flux<Long> infiniteFlux = Flux.interval(Duration.ofMillis(200))
                .take(3)
                .log();
        StepVerifier.create(infiniteFlux)
                .expectSubscription()
                .expectNext(0L, 1L, 2L)
                .verifyComplete();
    }

    @Test
    public void InfiniteSequenceTest_WithMap() throws InterruptedException {
        Flux<Integer> infiniteFlux = Flux.interval(Duration.ofMillis(200))
                .map(l -> new Integer(l.intValue()))
                .take(3)
                .log();
        StepVerifier.create(infiniteFlux)
                .expectSubscription()
                .expectNext(0, 1, 2)
                .verifyComplete();
    }
}
