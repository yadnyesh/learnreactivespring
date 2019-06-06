package io.yadnyesh.learnreactivespring.controller.v1;

import io.yadnyesh.learnreactivespring.domain.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@Slf4j
public class ItemController {

    public Flux<Item> getAllItems() {

    }
}
