package io.yadnyesh.learnreactivespring.repository;

import io.yadnyesh.learnreactivespring.domain.Item;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ItemReactiveRepository extends ReactiveMongoRepository<Item, String> {
    Mono<Item> findByDescription(String description);
}
