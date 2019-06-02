package io.yadnyesh.learnreactivespring.fluxandmonoplayground;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxAndMonoTest {

    @Test
    public void fluxTest() {
        Flux<String> stringFlux = Flux.just("Spring ", "Spring Boot ", "Reactive Spring ")
//                .concatWith(Flux.error(new RuntimeException("Exception has occurred")))
                .concatWith(Flux.just("After Error"))
                .log();
        stringFlux.subscribe(System.out::println, (e) -> System.err.println(e), () -> System.out.println("Completed"));
    }

    @Test
    public void fluxTestElements_WithoutError() {
        Flux<String> stringFlux = Flux.just("Spring ", "Spring Boot ", "Reactive Spring ")
                .log();
        StepVerifier.create(stringFlux)
                .expectNext("Spring ")
                .expectNext("Spring Boot ")
                .expectNext("Reactive Spring ")
                .verifyComplete();
    }

    @Test
    public void fluxTestElements_WithError() {
        Flux<String> stringFlux = Flux.just("Spring ", "Spring Boot ", "Reactive Spring ")
                .concatWith(Flux.error(new RuntimeException("Exception has occurred")))
                .log();
        StepVerifier.create(stringFlux)
                .expectNext("Spring ")
                .expectNext("Spring Boot ")
                .expectNext("Reactive Spring ")
                //.expectError(RuntimeException.class)
                .expectErrorMessage("Exception has occurred")
                .verify();
    }

    @Test
    public void fluxElementsCount() {
        Flux<String> stringFlux = Flux.just("Spring ", "Spring Boot ", "Reactive Spring ")
                //.concatWith(Flux.error(new RuntimeException("Exception has occurred")))
                .log();
        StepVerifier.create(stringFlux)
                .expectNextCount(3)
                //.expectErrorMessage("Exception has occurred")
                .verifyComplete();
    }

    @Test
    public void monoTest() {
        Mono<String> stringMono = Mono.just("Spring");
        StepVerifier.create(stringMono.log())
                .expectNext("Spring")
                .verifyComplete();
    }
}
