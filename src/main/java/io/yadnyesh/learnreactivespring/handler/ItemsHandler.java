package io.yadnyesh.learnreactivespring.handler;

import io.yadnyesh.learnreactivespring.repository.ItemReactiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ItemsHandler {

    @Autowired
    ItemReactiveRepository itemReactiveRepository;
}
