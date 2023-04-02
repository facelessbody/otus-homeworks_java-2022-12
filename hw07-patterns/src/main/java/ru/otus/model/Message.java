package ru.otus.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder(toBuilder = true)
@Getter
@ToString(doNotUseGetters = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Message {
    @EqualsAndHashCode.Include
    private final long id;
    private final String field1;
    private final String field2;
    private final String field3;
    private final String field4;
    private final String field5;
    private final String field6;
    private final String field7;
    private final String field8;
    private final String field9;
    private final String field10;
    private final String field11;
    private final String field12;
    private final ObjectForMessage field13;

    public static MessageBuilder builder(long id) {
        return new MessageBuilder().id(id);
    }

    public static Message copyOf(Message other) {
        var copiedField13 = ObjectForMessage.copyOf(other.field13);
        return other.toBuilder().field13(copiedField13).build();
    }
}
