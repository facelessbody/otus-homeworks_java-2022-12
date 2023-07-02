package ru.nzhilik.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.nzhilik.domain.Message;

public interface DataStore {

    Mono<Message> saveMessage(Message message);

    Flux<Message> loadMessages();
    Flux<Message> loadMessages(String roomId);
}
