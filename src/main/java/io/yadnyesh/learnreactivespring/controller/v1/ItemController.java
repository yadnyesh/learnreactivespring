package io.yadnyesh.learnreactivespring.controller.v1;

import io.yadnyesh.learnreactivespring.constants.ItemConstants;
import io.yadnyesh.learnreactivespring.domain.Item;
import io.yadnyesh.learnreactivespring.repository.ItemReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class ItemController {

//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
//        log.error("Exception caught in  ControllerExceptionHandler : {} ", ex);
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
//    }

    @Autowired
    ItemReactiveRepository itemReactiveRepository;

    @GetMapping(ItemConstants.ITEM_END_POINT_V1)
    public Flux<Item> getAllItems() {
        return itemReactiveRepository.findAll();
    }

    @GetMapping(ItemConstants.ITEM_END_POINT_V1 + "/{id}")
    public Mono<ResponseEntity<Item>> getItemById(@PathVariable String id) {

        return itemReactiveRepository.findById(id)
                .map(item -> new ResponseEntity<>(item, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(ItemConstants.ITEM_END_POINT_V1)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Item> createItem(@RequestBody Item item) {
        return itemReactiveRepository.save(item);
    }

    @DeleteMapping(ItemConstants.ITEM_END_POINT_V1 + "/{id}")
    public Mono<Void> deleteItem(@PathVariable String id) {
        return itemReactiveRepository.deleteById(id);
    }

    @GetMapping(ItemConstants.ITEM_END_POINT_V1 + "/runtimeexception")
    public Flux<Item> runtimeException() {
        return itemReactiveRepository.findAll()
                .concatWith(Mono.error(new RuntimeException("Delibrate Runtime Exception")));
    }


    @PutMapping(ItemConstants.ITEM_END_POINT_V1 + "/{id}")
    public Mono<ResponseEntity<Item>> updateItem(@PathVariable String id, @RequestBody Item item) {
        return itemReactiveRepository.findById(id)
                                    .flatMap((presentItem) -> {
                                        presentItem.setPrice(item.getPrice());
                                        presentItem.setDescription(item.getDescription());
                                        return itemReactiveRepository.save(presentItem);
                                    }).map(updatedItem -> new ResponseEntity<>(updatedItem, HttpStatus.OK))
                                    .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
