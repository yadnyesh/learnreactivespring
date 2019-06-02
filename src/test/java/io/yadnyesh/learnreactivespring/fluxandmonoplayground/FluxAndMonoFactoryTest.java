package io.yadnyesh.learnreactivespring.fluxandmonoplayground;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class FluxAndMonoFactoryTest {

    List<String> stringNames = Arrays.asList("Adam", "Eve", "Nanna", "Tamma", "Rakul");

    @Test
    public void fluxUsingIterable() {
        Flux<String> namesFlux = Flux.fromIterable(stringNames).log();

        StepVerifier.create(namesFlux)
                .expectNext("Adam", "Eve", "Nanna", "Tamma", "Rakul")
                .verifyComplete();

    }
}
