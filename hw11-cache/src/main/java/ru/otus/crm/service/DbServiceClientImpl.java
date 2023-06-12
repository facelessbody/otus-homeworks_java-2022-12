package ru.otus.crm.service;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.cache.HwCache;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.sessionmanager.TransactionManager;
import ru.otus.crm.model.Client;

@RequiredArgsConstructor
public class DbServiceClientImpl implements DBServiceClient {
    private static final Logger log = LoggerFactory.getLogger(DbServiceClientImpl.class);

    private final DataTemplate<Client> clientDataTemplate;
    private final TransactionManager transactionManager;
    private final HwCache<String, Client> clientCache;

    @Override
    public Client saveClient(Client client) {
        var saved = transactionManager.doInTransaction(session -> {
            var clientCloned = client.clone();
            if (client.getId() == null) {
                clientDataTemplate.insert(session, clientCloned);
                log.info("created client: {}", clientCloned);
                return clientCloned;
            }
            clientDataTemplate.update(session, clientCloned);
            log.info("updated client: {}", clientCloned);
            return clientCloned;
        });
        clientCache.put(String.valueOf(saved.getId()), saved);
        return saved;
    }

    @Override
    public Optional<Client> getClient(long id) {
        var cached = clientCache.get(String.valueOf(id));
        if (cached != null) {
            return Optional.of(cached);
        }
        var loaded = transactionManager.doInReadOnlyTransaction(session -> {
            var clientOptional = clientDataTemplate.findById(session, id);
            log.info("client: {}", clientOptional);
            return clientOptional;
        });
        loaded.ifPresent(c -> clientCache.put(String.valueOf(c.getId()), c));
        return loaded;
    }

    @Override
    public List<Client> findAll() {
        var allClients = transactionManager.doInReadOnlyTransaction(session -> {
            var clientList = clientDataTemplate.findAll(session);
            log.info("clientList:{}", clientList);
            return clientList;
        });
        for (var client : allClients) {
            clientCache.put(String.valueOf(client.getId()), client);
        }
        return allClients;
    }
}
