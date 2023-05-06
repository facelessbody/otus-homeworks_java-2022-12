package ru.otus.model;

import java.util.List;

public class ObjectForMessage {
    private List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public static ObjectForMessage copyOf(ObjectForMessage other) {
        var copied = new ObjectForMessage();
        copied.setData(List.copyOf(other.data));
        return copied;
    }
}
