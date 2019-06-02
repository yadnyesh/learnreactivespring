package io.yadnyesh.learnreactivespring.fluxandmonoplayground;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

public class FluxAndMonoFilterTest
{
    List<String> stringNames = Arrays.asList("Adam", "Eve", "Nanna", "Tamma", "Rakul");

    @Test
    public void filterTest() {
       Flux<String> stringFlux =  Flux.fromIterable(stringNames)
               .filter(s -> s.startsWith("A"))
               .log();

        StepVerifier.create(stringFlux)
                .expectNext("Adam")
                .verifyComplete();
    }
}
