package io.yadnyesh.learnreactivespring.fluxandmonoplayground;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.scheduler.VirtualTimeScheduler;

import java.time.Duration;

public class FluxAndMonoCombineTest {

    @Test
    public void combineUsingMerge() {
        Flux<String> flux1 = Flux.just("A", "B", "C");
        Flux<String> flux2 = Flux.just("A", "B", "C");

        Flux<String> mergedFlux = Flux.merge(flux1,flux2);

        StepVerifier.create(mergedFlux.log())
                .expectSubscription()
                .expectNext("A", "B", "C", "A", "B", "C")
                .verifyComplete();
    }

    @Test
    public void combineUsingMerge_withDelay() {

        VirtualTimeScheduler.getOrSet();

        Flux<String> flux1 = Flux.just("A", "B", "C").delayElements(Duration.ofSeconds(1));
        Flux<String> flux2 = Flux.just("D", "E", "F");

        Flux<String> mergedFlux = Flux.merge(flux1,flux2);

        StepVerifier.withVirtualTime(() -> mergedFlux.log())
                    .expectSubscription()
                    .thenAwait(Duration.ofSeconds(6))
                    .expectNextCount(6)
                 .verifyComplete();
//        StepVerifier.create(mergedFlux.log())
//                .expectSubscription()
//                .expectNextCount(6)
//                //.expectNext("A", "B", "C", "D", "E", "F")
//                .verifyComplete();
    }

    @Test
    public void combineUsingMerge_withConcat() {
        Flux<String> flux1 = Flux.just("A", "B", "C").delayElements(Duration.ofSeconds(1));
        Flux<String> flux2 = Flux.just("D", "E", "F");

        Flux<String> mergedFlux = Flux.concat(flux1,flux2);

        StepVerifier.create(mergedFlux.log())
                .expectSubscription()
                //.expectNextCount(6)
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();
    }

    @Test
    public void combineUsingMerge_withZip() {
        Flux<String> flux1 = Flux.just("A", "B", "C");
        Flux<String> flux2 = Flux.just("D", "E", "F");

        Flux<String> mergedFlux = Flux.zip(flux1,flux2, (t1, t2) -> {
            return t1.concat(t2);
        });

        StepVerifier.create(mergedFlux.log())
                .expectSubscription()
                //.expectNextCount(6)
                .expectNext("AD", "BE", "CF")
                .verifyComplete();
    }
}
