package ru.otus.servlet.dto;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;

public record ClientDto(Client client) {
    public String getId() {
        return Optional.ofNullable(client.getId())
                .map(String::valueOf)
                .orElse("");
    }

    public String getName() {
        return client.getName();
    }

    public String getAddress() {
        return Optional.ofNullable(client.getAddress())
                .map(Address::getStreet)
                .orElse("");
    }

    public String getPhones() {
        return Stream.ofNullable(client.getPhones())
                .flatMap(Collection::stream)
                .map(Phone::getNumber)
                .collect(Collectors.joining(", "));
    }
}
