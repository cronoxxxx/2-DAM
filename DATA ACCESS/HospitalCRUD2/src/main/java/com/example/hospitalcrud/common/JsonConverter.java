package com.example.hospitalcrud.common;

import com.example.hospitalcrud.common.config.Configuration;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Component
public class JsonConverter {
    private final Configuration configuration;

    public JsonConverter(Configuration configuration) {
        this.configuration = configuration;
    }

    public List<FetchType> loadFetchTypes() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Type userListType = new TypeToken<ArrayList<FetchType>>() {
        }.getType();
        List<FetchType> fetchTypes;
        try {
            fetchTypes = gson.fromJson(new FileReader(configuration.getPathJsonUsers()), userListType);
            return fetchTypes;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public boolean saveFetchTypes(List<FetchType> fetchTypes) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String path = configuration.getPathJsonUsers();
        try (FileWriter fileWriter = new FileWriter(path)) {
            gson.toJson(fetchTypes, fileWriter);
            return true;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

}
