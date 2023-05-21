package ru.otus.core.repository;

import java.util.function.Supplier;

import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;

public class EntityClassesSupplier implements Supplier<Class<?>[]> {

    @Override
    public Class<?>[] get() {
        return new Class[]{
                Client.class,
                Address.class,
                Phone.class
        };
    }
}
