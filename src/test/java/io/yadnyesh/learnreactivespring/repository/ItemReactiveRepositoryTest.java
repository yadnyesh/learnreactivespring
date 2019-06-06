package io.yadnyesh.learnreactivespring.repository;

import io.yadnyesh.learnreactivespring.domain.Item;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

@DataMongoTest
@RunWith(SpringRunner.class)
public class ItemReactiveRepositoryTest {

    List<Item> itemList = Arrays.asList(new Item(null, "Samsung TV", 400.0),
            new Item(null, "LG TV", 500.0),
            new Item(null, "Apple Watch", 299.99),
            new Item(null, "Beats Headphones", 149.99));

    @Autowired
    ItemReactiveRepository itemReactiveRepository;

    @Before
    public void setUp() {
        itemReactiveRepository.deleteAll()
                .thenMany(Flux.fromIterable(itemList))
                .flatMap(itemReactiveRepository::save)
                .doOnNext(item -> {
                    System.out.println("Saved Item is: " + item.toString());
                })
                .blockLast();
    }

    @Test
    public void getAllItems() {

        StepVerifier.create(itemReactiveRepository.findAll())
                .expectSubscription()
                .expectNextCount(4)
                .verifyComplete();
    }
}
