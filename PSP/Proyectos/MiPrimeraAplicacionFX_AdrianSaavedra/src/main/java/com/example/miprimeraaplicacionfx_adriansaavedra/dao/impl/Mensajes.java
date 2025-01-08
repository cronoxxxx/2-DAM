package com.example.miprimeraaplicacionfx_adriansaavedra.dao.impl;

import com.example.miprimeraaplicacionfx_adriansaavedra.common.config.Configuracion;
import com.example.miprimeraaplicacionfx_adriansaavedra.domain.model.Mensaje;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Getter
public class Mensajes {
    private final List<Mensaje> mensajeList;

    public Mensajes() {
       this.mensajeList = loadMensajes();
    }
    public List<Mensaje> loadMensajes() {

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) -> LocalDateTime.parse(json.getAsJsonPrimitive().getAsString()))
                .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (localDateTime, type, jsonSerializationContext) -> new JsonPrimitive(localDateTime.toString()))
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, type, jsonDeserializationContext) -> LocalDate.parse(json.getAsJsonPrimitive().getAsString()))
                .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (localDate, type, jsonSerializationContext) -> new JsonPrimitive(localDate.toString())
                ).create();
        Type userListType = new TypeToken<ArrayList<Mensaje>>() {
        }.getType();

        List<Mensaje> mensajes = null;
        try {
            mensajes = gson.fromJson(
                    new FileReader(new Configuracion().loadPathPropertiesMessages()),
                    userListType);
        } catch (FileNotFoundException e) {
            //log.error(e.getMessage(), e);
        }
        return mensajes;
    }

    public boolean saveMensajes(List<Mensaje> mensajes) {
        Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) -> LocalDateTime.parse(json.getAsJsonPrimitive().getAsString()))
                .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (localDateTime, type, jsonSerializationContext) -> new JsonPrimitive(localDateTime.toString()))
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, type, jsonDeserializationContext) -> LocalDate.parse(json.getAsJsonPrimitive().getAsString()))
                .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (localDate, type, jsonSerializationContext) -> new JsonPrimitive(localDate.toString())
                ).create();

        try (FileWriter fw = new FileWriter(new Configuracion().loadPathPropertiesMessages())) {
            gson.toJson(mensajes, fw);
        } catch (IOException e) {
            //log.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

}
