package ru.otus;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.cfg.Configuration;
import ru.otus.core.repository.DataTemplateHibernate;
import ru.otus.core.repository.EntityClassesSupplier;
import ru.otus.core.repository.HibernateUtils;
import ru.otus.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.crm.db.migrations.MigrationsExecutorFlyway;
import ru.otus.crm.model.Client;
import ru.otus.crm.service.DbServiceClientImpl;

@Slf4j
public class Application {

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
        var dbServiceClient = new DbServiceClientImpl(transactionManager, clientTemplate);
        // todo: add business logic
    }
}
