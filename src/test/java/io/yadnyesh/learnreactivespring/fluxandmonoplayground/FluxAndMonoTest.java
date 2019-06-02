package io.yadnyesh.learnreactivespring.fluxandmonoplayground;

import org.junit.Test;
import reactor.core.publisher.Flux;

public class FluxAndMonoTest {

    @Test
    public void fluxTest() {
        Flux.just("Spring ", "Spring Boot ", "Reactive Spring ")
                .map(s -> s.concat("reactive"))
                .subscribe(System.out::println);
    }
}
