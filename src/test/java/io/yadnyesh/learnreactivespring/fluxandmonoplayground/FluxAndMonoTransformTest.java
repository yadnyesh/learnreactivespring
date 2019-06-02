package io.yadnyesh.learnreactivespring.fluxandmonoplayground;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

public class FluxAndMonoTransformTest {

    List<String> stringNames = Arrays.asList("Adam", "Eve", "Nanna", "Tamma", "Rakul");

    @Test
    public void transformUsingMap(){
        Flux<String> namesFlux = Flux.fromIterable(stringNames)
                .map(s -> s.toUpperCase())
                .log();
        StepVerifier.create(namesFlux)
                .expectNext("ADAM", "EVE", "NANNA", "TAMMA", "RAKUL")
                .verifyComplete();

    }

    @Test
    public void getLengthOfStrings(){
        Flux<Integer> namesFlux = Flux.fromIterable(stringNames)
                .map(s -> s.length())
                .log();
        StepVerifier.create(namesFlux)
                .expectNext(4, 3, 5, 5, 5)
                .verifyComplete();

    }

    @Test
    public void getLengthOfStrings_repeat(){
        Flux<Integer> namesFlux = Flux.fromIterable(stringNames)
                .map(s -> s.length())
                .repeat(1)
                .log();
        StepVerifier.create(namesFlux)
                .expectNext(4, 3, 5, 5, 5, 4, 3, 5, 5, 5)
                .verifyComplete();

    }

    @Test
    public void fluxTransformWithMapAndFilter(){
        Flux<Integer> namesFlux = Flux.fromIterable(stringNames)
                .filter(s -> s.length() > 4)
                .map(s -> s.length())
                .repeat(1)
                .log();
        StepVerifier.create(namesFlux)
                .expectNext(5, 5, 5, 5, 5, 5)
                .verifyComplete();

    }
}
