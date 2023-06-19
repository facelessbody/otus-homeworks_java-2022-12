package ru.otus.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import ru.otus.core.templateprocessor.TemplateProcessor;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.servlet.dto.ClientDto;

@RequiredArgsConstructor
public class AdminServlet extends HttpServlet {
    private static final String CLIENTS_PAGE_TEMPLATE = "admin.html";
    private static final String TEMPLATE_ATTR_CLIENT = "clientsList";
    private final TemplateProcessor templateProcessor;
    private final DBServiceClient clientService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();

        var clientsList = clientService.findAll()
                .stream()
                .map(ClientDto::new)
                .toList();
        paramsMap.put(TEMPLATE_ATTR_CLIENT, clientsList);

        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(CLIENTS_PAGE_TEMPLATE, paramsMap));
    }
}
