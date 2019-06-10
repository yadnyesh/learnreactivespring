package io.yadnyesh.learnreactivespring.router;

import io.yadnyesh.learnreactivespring.constants.ItemConstants;
import io.yadnyesh.learnreactivespring.handler.ItemsHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class ItemsRouter {

    @Bean
    public RouterFunction<ServerResponse> itemsRoute (ItemsHandler itemsHandler) {

        return RouterFunctions.route(GET(ItemConstants.ITEM_FUNCTIONAL_END_POINT_V1)
                                .and(accept(MediaType.APPLICATION_JSON_UTF8))
                                ,itemsHandler::getAllItems)
                                .andRoute(GET(ItemConstants.ITEM_FUNCTIONAL_END_POINT_V1 + "/{id}")
                                .and(accept(MediaType.APPLICATION_JSON_UTF8)), itemsHandler::getItemById)
                                .andRoute(POST(ItemConstants.ITEM_FUNCTIONAL_END_POINT_V1).and(accept(MediaType.APPLICATION_JSON_UTF8))
                                ,itemsHandler::createItem)
                                .andRoute(DELETE(ItemConstants.ITEM_FUNCTIONAL_END_POINT_V1 + "/{id}").and(accept(MediaType.APPLICATION_JSON_UTF8))
                                ,itemsHandler::deleteItem);
    }
}
