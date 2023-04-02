package ru.otus.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ObjectForMessage {
    private List<String> data;

    public static ObjectForMessage copyOf(ObjectForMessage other) {
        var copied = new ObjectForMessage();
        copied.setData(List.copyOf(other.data));
        return copied;
    }
}
