package io.yadnyesh.learnreactivespring.fluxandmonoplayground;

import org.junit.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class FluxAndMonoWithTimeTest {

    @Test
    public void FluxAndMonoTimeTest() {
        Flux<Long> infiniteFlux = Flux.interval(Duration.ofMillis(200))
                .log();
        infiniteFlux.subscribe((e) -> System.out.println("Current Element is: " + e));
    }
}
