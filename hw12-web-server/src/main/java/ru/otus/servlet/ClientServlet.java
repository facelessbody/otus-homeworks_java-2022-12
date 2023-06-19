package ru.otus.servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.service.DBServiceClient;

import static java.util.function.Predicate.not;

@RequiredArgsConstructor
public class ClientServlet extends HttpServlet {
    private final DBServiceClient clientService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var client = new Client();
        client.setName(req.getParameter("clientName"));
        Optional.ofNullable(req.getParameter("clientAddress"))
                .filter(not(String::isBlank))
                .map(street -> new Address(null, street, client))
                .ifPresent(client::setAddress);

        var phoneNumbers = Optional.ofNullable(req.getParameter("clientPhones"))
                .filter(not(String::isBlank))
                .map(s -> s.split(","))
                .orElseGet(() -> new String[0]);
        var phones = Arrays.stream(phoneNumbers)
                .map(String::strip)
                .map(x -> new Phone(null, x, client))
                .toList();
        client.setPhones(phones);

        clientService.saveClient(client);
        resp.sendRedirect("admin");
    }
}
