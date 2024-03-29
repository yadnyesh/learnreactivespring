package io.yadnyesh.learnreactivespring.repository;

import io.yadnyesh.learnreactivespring.domain.Item;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

@DataMongoTest
@RunWith(SpringRunner.class)
@DirtiesContext
public class ItemReactiveRepositoryTest {

    List<Item> itemList = Arrays.asList(new Item(null, "Samsung TV", 400.0),
            new Item(null, "LG TV", 500.0),
            new Item(null, "Apple Watch", 299.99),
            new Item(null, "Beats Headphones", 149.99),
            new Item("ABC", "Bose Headphones", 149.99));

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
                .expectNextCount(5)
                .verifyComplete();
    }

    @Test
    public void getItemById() {
        StepVerifier.create(itemReactiveRepository.findById("ABC"))
                .expectSubscription()
                .expectNextMatches(item -> item.getDescription().equals("Bose Headphones"))
                .verifyComplete();
    }

    @Test
    public void findItemByDescription() {
        StepVerifier.create(itemReactiveRepository.findByDescription("Bose Headphones").log())
                .expectSubscription()
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    public void saveItem() {
        Item testItem = new Item(null, "Google Home Mini", 30.00);
        Mono<Item> savedItem = itemReactiveRepository.save(testItem);
        StepVerifier.create(savedItem.log())
                .expectSubscription()
                .expectNextMatches(item -> (item.getId() != null && item.getDescription().equals("Google Home Mini")))
                .verifyComplete();
    }

    @Test
    public void updateItem() {
        double newPrice = 520.00;
        Mono<Item> itemToUpdate = itemReactiveRepository.findByDescription("LG TV")
                                .map(item -> {
                                      item.setPrice(newPrice);
                                      return item;
                                })
                                .flatMap(item -> {
                                   return itemReactiveRepository.save(item);
                                });

        StepVerifier.create(itemToUpdate)
                .expectSubscription()
                .expectNextMatches(item -> item.getPrice() == 520.0)
                .verifyComplete();

    }

    @Test
    public void deleteItemById() {
        Mono<Void> deletedItem = itemReactiveRepository.findById("ABC")
              .map(Item::getId)
              .flatMap(itemId -> {
                  return itemReactiveRepository.deleteById(itemId);
              });
        StepVerifier.create(deletedItem.log())
              .expectSubscription()
              .verifyComplete();

        StepVerifier.create(itemReactiveRepository.findAll().log("The new item list: "))
                .expectSubscription()
                .expectNextCount(4)
                .verifyComplete();

    }

    @Test
    public void deleteItem() {
        Mono<Void> deletedItem = itemReactiveRepository.findById("ABC")
                .flatMap(item -> {
                    return itemReactiveRepository.delete(item);
                });
        StepVerifier.create(deletedItem.log())
                .expectSubscription()
                .verifyComplete();

        StepVerifier.create(itemReactiveRepository.findAll().log("The new item list: "))
                .expectSubscription()
                .expectNextCount(4)
                .verifyComplete();

    }
}
