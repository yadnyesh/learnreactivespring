package io.yadnyesh.learnreactivespring.initialize;

import io.yadnyesh.learnreactivespring.domain.Item;
import io.yadnyesh.learnreactivespring.domain.ItemCapped;
import io.yadnyesh.learnreactivespring.repository.ItemReactiveCappedRepository;
import io.yadnyesh.learnreactivespring.repository.ItemReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

@Component
@Profile("!test")
@Slf4j
public class ItemDataInitializer implements CommandLineRunner {

    @Autowired
    ItemReactiveRepository itemReactiveRepository;

    @Autowired
    MongoOperations mongoOperations;

    @Autowired
    ItemReactiveCappedRepository itemReactiveCappedRepository;



    @Override
    public void run(String... args) throws Exception {
        initialDataSetup();
        createCappedCollection();
        dataSetupForCappedCollection();
    }

    private void createCappedCollection() {
        mongoOperations.dropCollection(ItemCapped.class);
        mongoOperations.createCollection(ItemCapped.class, CollectionOptions.empty().maxDocuments(20).size(50000).capped());
    }

    public List<Item> data() {

       return Arrays.asList(new Item(null, "Samsung TV", 400.0),
                new Item(null, "LG TV", 500.0),
                new Item(null, "Apple Watch", 299.99),
                new Item(null, "Beats Headphones", 149.99),
                new Item("ABC", "Bose Headphones", 149.99));
    }

    private void dataSetupForCappedCollection() {
        Flux<ItemCapped> itemCappedFlux = Flux.interval(Duration.ofSeconds(2))
                .map(i -> new ItemCapped(null, "Random Item" + i, (100.00 + i)));
        itemReactiveCappedRepository.insert(itemCappedFlux)
                .subscribe(itemCapped -> {
                   log.info("Inserted item is: " + itemCappedFlux);
                });
    }

    private void initialDataSetup() {
        itemReactiveRepository.deleteAll()
                .thenMany(Flux.fromIterable(data()))
                .flatMap(itemReactiveRepository::save)
                .thenMany(itemReactiveRepository.findAll())
                .subscribe(item -> {
                    System.out.println("Item inserted - CMD Run: " + item.toString());
                });

    }
}
