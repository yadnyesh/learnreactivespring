package io.yadnyesh.learnreactivespring.fluxandmonoplayground;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static reactor.core.scheduler.Schedulers.parallel;

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

    @Test
    public void transformUsingFlatMap() {
        Flux<String> stringFlux = Flux.fromIterable(Arrays.asList("A","B","C","D","E","F","G","H","I","J","K"))
                .flatMap(s -> {
                    return Flux.fromIterable(convertToList(s));
                }) //db or external call that returns a flux
                .log();

        StepVerifier.create(stringFlux)
                .expectNextCount(22)
                .verifyComplete();
    }

    private List<String> convertToList(String s) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Arrays.asList(s, "newValue");
    }

    @Test
    public void transformUsingFlatMap_usingParallel() {
        Flux<String> stringFlux = Flux.fromIterable(Arrays.asList("A","B","C","D","E","F","G","H","I","J","K"))
                .window(2)
                .flatMap((s) ->
                    s.map(this::convertToList).subscribeOn(parallel())) //db or external call that returns a flux
                .flatMap(s -> Flux.fromIterable(s))
                .log();

        StepVerifier.create(stringFlux)
                .expectNextCount(22)
                .verifyComplete();
    }

    @Test
    public void transformUsingFlatMap_usingParallel_sequenceOfInputs() {
        Flux<String> stringFlux = Flux.fromIterable(Arrays.asList("A","B","C","D","E","F","G","H","I","J","K"))
                .window(2)
                .concatMap((s) ->
                        s.map(this::convertToList).subscribeOn(parallel())) //db or external call that returns a flux
                .flatMap(s -> Flux.fromIterable(s))
                .log();

        StepVerifier.create(stringFlux)
                .expectNextCount(22)
                .verifyComplete();
    }
}
