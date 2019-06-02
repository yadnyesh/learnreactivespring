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
}
