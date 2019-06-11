package io.yadnyesh.learnreactivespring.handler;

import io.yadnyesh.learnreactivespring.domain.Item;
import io.yadnyesh.learnreactivespring.repository.ItemReactiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@Component
public class ItemsHandler {

    @Autowired
    ItemReactiveRepository itemReactiveRepository;



    public Mono<ServerResponse> getAllItems(ServerRequest serverRequest){
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(itemReactiveRepository.findAll(), Item.class);

    }

    public Mono<ServerResponse> getItemById(ServerRequest serverRequest){
        String itemId = serverRequest.pathVariable("id");

        Mono<Item> itemMono = itemReactiveRepository.findById(itemId);

        return itemMono.flatMap(item ->
                ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(fromObject(item)))
                .switchIfEmpty(ServerResponse.notFound().build());

    }

    public Mono<ServerResponse> createItem(ServerRequest serverRequest){
        Mono<Item> itemToBeInserted = serverRequest.bodyToMono(Item.class);
        return itemToBeInserted.flatMap(item ->
                ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(itemReactiveRepository.save(item), Item.class));
    }

    public Mono<ServerResponse> deleteItem(ServerRequest serverRequest){
        String itemId = serverRequest.pathVariable("id");

        //Mono<Item> itemToBeInserted = serverRequest.bodyToMono(Item.class);
        Mono<Void> deleteItem = itemReactiveRepository.deleteById(itemId);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(deleteItem, Void.class);
    }

    public Mono<ServerResponse> updateItem(ServerRequest serverRequest){
        String itemId = serverRequest.pathVariable("id");
        Mono<Item> updatedItem = serverRequest.bodyToMono(Item.class)
                .flatMap(item -> {
                   Mono<Item> itemMono = itemReactiveRepository.findById(itemId)
                            .flatMap(currentItem -> {
                                currentItem.setDescription(item.getDescription());
                                currentItem.setPrice(item.getPrice());
                                return itemReactiveRepository.save(currentItem);
                            });
                   return itemMono;
                });

        return updatedItem.flatMap(item ->
           ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .body(fromObject(item))
            .switchIfEmpty(ServerResponse.notFound().build())
        );


//        Mono<Item> itemToBeInserted = serverRequest.bodyToMono(Item.class);
//
//        Mono<Void> deleteItem = itemReactiveRepository.deleteById(itemId);
//        return ServerResponse.ok()
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .body(deleteItem, Void.class);
    }
}
