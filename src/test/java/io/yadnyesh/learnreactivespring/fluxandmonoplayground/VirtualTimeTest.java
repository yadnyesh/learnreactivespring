package io.yadnyesh.learnreactivespring.fluxandmonoplayground;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

public class VirtualTimeTest {

    @Test
    public void testingWithoutVirtualTime() {
        Flux<Long> longFlux = Flux.interval(Duration.ofSeconds(1)).take(3);
        StepVerifier.create(longFlux.log())
                .expectSubscription()
                .expectNext(0L,1L, 2L)
                .verifyComplete();
    }

}
