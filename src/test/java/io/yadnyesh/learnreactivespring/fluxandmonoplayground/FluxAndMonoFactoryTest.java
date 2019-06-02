package io.yadnyesh.learnreactivespring.fluxandmonoplayground;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class FluxAndMonoFactoryTest {

    List<String> stringNames = Arrays.asList("Adam", "Eve", "Nanna", "Tamma", "Rakul");

    @Test
    public void fluxUsingIterable() {
        Flux<String> namesFlux = Flux.fromIterable(stringNames).log();

        StepVerifier.create(namesFlux)
                .expectNext("Adam", "Eve", "Nanna", "Tamma", "Rakul")
                .verifyComplete();

    }

    @Test
    public void justOrEmptyMono() {
        Mono<String> monoString = Mono.justOrEmpty(null);
        StepVerifier.create(monoString.log())
                .verifyComplete();
    }

    @Test
    public void monoUsingSupplier() {
        Supplier<String> stringSupplier = () -> "yadnyesh";
        Mono<String> stringMono = Mono.fromSupplier(stringSupplier);
        StepVerifier.create(stringMono.log())
                .expectNext("yadnyesh")
                .verifyComplete();


    }
}
