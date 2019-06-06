package io.yadnyesh.learnreactivespring.repository;

import io.yadnyesh.learnreactivespring.domain.Item;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ItemReactiveRepository extends ReactiveMongoRepository<Item, String> {

}
