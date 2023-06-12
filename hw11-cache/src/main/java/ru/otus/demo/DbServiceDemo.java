package ru.otus.demo;

import java.util.List;

import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.cache.HwCacheImpl;
import ru.otus.core.cache.HwListener;
import ru.otus.core.repository.DataTemplateHibernate;
import ru.otus.core.repository.EntityClassesSupplier;
import ru.otus.core.repository.HibernateUtils;
import ru.otus.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.crm.db.migrations.MigrationsExecutorFlyway;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.service.DbServiceClientImpl;

public class DbServiceDemo {

    private static final Logger log = LoggerFactory.getLogger(DbServiceDemo.class);
    public static final HwListener<Long, Client> LONG_CLIENT_HW_LISTENER = (key, value, action) ->
            log.info("key:{}, value:{}, action: {}", key, value, action);

    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    public static void main(String[] args) {

        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");

        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();

        var entityClassesSupplier = new EntityClassesSupplier();
        var sessionFactory = HibernateUtils.buildSessionFactory(configuration, entityClassesSupplier.get());

        var transactionManager = new TransactionManagerHibernate(sessionFactory);
///
        var clientTemplate = new DataTemplateHibernate<>(Client.class);
///
        var clientCache = new HwCacheImpl<Long, Client>();
        clientCache.addListener(LONG_CLIENT_HW_LISTENER);

        var dbServiceClient = new DbServiceClientImpl(clientTemplate, transactionManager, clientCache);
        dbServiceClient.saveClient(new Client("dbServiceFirst"));

        var clientSecond = dbServiceClient.saveClient(
                new Client(null, "dbServiceSecond",
                        new Address(null, "somewhere"),
                        List.of(
                                new Phone(null, "123"),
                                new Phone(null, "456"))
                ));

        log.info("All clients");
        dbServiceClient.findAll().forEach(client -> log.info("client:{}", client));


        var clientSecondSelected = dbServiceClient.getClient(clientSecond.getId())
                .orElseThrow(() -> new RuntimeException("Client not found, id:" + clientSecond.getId()));
        log.info("clientSecondSelected:{}", clientSecondSelected);

        dbServiceClient.saveClient(new Client(clientSecondSelected.getId(), "dbServiceSecondUpdated"));
        var clientUpdated = dbServiceClient.getClient(clientSecondSelected.getId())
                .orElseThrow(() -> new RuntimeException("Client not found, id:" + clientSecondSelected.getId()));
        log.info("clientUpdated:{}", clientUpdated);

        clientCache.removeListener(LONG_CLIENT_HW_LISTENER);
    }
}
