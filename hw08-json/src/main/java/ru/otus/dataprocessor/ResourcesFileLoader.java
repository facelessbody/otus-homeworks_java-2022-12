package ru.otus.dataprocessor;

import ru.otus.model.Measurement;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.common.io.Resources;
import com.google.gson.Gson;

public class ResourcesFileLoader implements Loader {
    private final String fileName;

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() {
        //читает файл, парсит и возвращает результат

        var resourceUrl = Resources.getResource(fileName);
        try {
            var json = Files.readString(Paths.get(resourceUrl.toURI()));
            return new Gson().fromJson(json, MeasurementsList.class);
        } catch (IOException | URISyntaxException e) {
            throw new FileProcessException(e);
        }
    }

    private static class MeasurementsList extends ArrayList<Measurement> {

    }
}
