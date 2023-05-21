package ru.otus;

import java.net.URL;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.security.HashLoginService;
import org.hibernate.cfg.Configuration;
import ru.otus.core.repository.DataTemplateHibernate;
import ru.otus.core.repository.EntityClassesSupplier;
import ru.otus.core.repository.HibernateUtils;
import ru.otus.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.core.templateprocessor.TemplateProcessorImpl;
import ru.otus.crm.db.migrations.MigrationsExecutorFlyway;
import ru.otus.crm.model.Client;
import ru.otus.crm.service.DbServiceClientImpl;
import ru.otus.helpers.FileSystemHelper;
import ru.otus.server.ClientsWebServer;

@Slf4j
public class Application {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";
    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";
    public static final String HASH_LOGIN_SERVICE_CONFIG_NAME = "realm.properties";

    public static final String REALM_NAME = "AnyRealm";

    public static void main(String[] args) throws Exception {
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

        var templatesProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);

        var loginService = new HashLoginService(REALM_NAME,
                FileSystemHelper.localFileNameOrResourceNameToFullPath(HASH_LOGIN_SERVICE_CONFIG_NAME));
        var clientsWebServer = new ClientsWebServer(WEB_SERVER_PORT, templatesProcessor, loginService, dbServiceClient);

        clientsWebServer.start();
        clientsWebServer.join();
    }
}
