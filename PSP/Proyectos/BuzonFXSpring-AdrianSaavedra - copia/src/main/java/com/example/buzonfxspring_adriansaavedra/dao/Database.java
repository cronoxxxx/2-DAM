package com.example.buzonfxspring_adriansaavedra.dao;


import com.example.buzonfxspring_adriansaavedra.common.config.Configuracion;
import com.example.buzonfxspring_adriansaavedra.domain.errors.ErrorApp;
import com.example.buzonfxspring_adriansaavedra.domain.errors.ErrorAppDataBase;
import com.example.buzonfxspring_adriansaavedra.domain.model.Grupo;
import com.example.buzonfxspring_adriansaavedra.domain.model.Mensaje;
import com.example.buzonfxspring_adriansaavedra.domain.model.Usuario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Component
@Log4j2
public class Database {
    private final Configuracion config;
    private final Gson gsonFile;

    public Database(Configuracion config, Gson gson) {
        this.config = config;
        this.gsonFile = gson;
    }

    public Either<ErrorApp, List<Grupo>> loadGrupos() {
        Type userListType = new TypeToken<List<Grupo>>() {
        }.getType();
        List<Grupo> grupos;
        try {
            grupos = gsonFile.fromJson(
                    new FileReader(config.getPathJsonGroup()),
                    userListType);
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
            return Either.left(ErrorAppDataBase.NO_CONNECTION);
        }
        return Either.right(grupos);
    }

    public Either<ErrorApp, Boolean> saveGrupos(List<Grupo> grupos) {
        String filePath = config.getPathJsonGroup();
        try (FileWriter fw = new FileWriter(filePath)) {
            gsonFile.toJson(grupos, fw);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return Either.left(ErrorAppDataBase.NO_CONNECTION);
        }
        return Either.right(true);
    }

    public Either<ErrorApp, List<Usuario>> loadUsuarios() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Type userListType = new TypeToken<ArrayList<Usuario>>() {}.getType();

        List<Usuario> usuarios;
        try {
            usuarios = gson.fromJson(
                    new FileReader(config.getPathJsonUsuarios()),
                    userListType);
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
            return Either.left(ErrorAppDataBase.NO_CONNECTION);
        }
        return Either.right(usuarios);
    }

    public Either<ErrorApp, Boolean> saveUsuarios(List<Usuario> usuarios) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String filePath = config.getPathJsonUsuarios();

        try (FileWriter fw = new FileWriter(filePath)) {
            gson.toJson(usuarios, fw);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return Either.left(ErrorAppDataBase.NO_CONNECTION);
        }
        return Either.right(true);
    }

    public Either<ErrorApp, List<Mensaje>> loadMensajes() {
        Type userListType = new TypeToken<ArrayList<Mensaje>>() {}.getType();
        try (FileReader reader = new FileReader(config.getPathJsonMessages())) {
            List<Mensaje> mensajes = gsonFile.fromJson(reader, userListType);
            return Either.right(mensajes != null ? mensajes : new ArrayList<>());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return Either.left(ErrorAppDataBase.NO_CONNECTION);
        }
    }

    public Either<ErrorApp, Boolean> saveMensajes(List<Mensaje> mensajes) {
        try (FileWriter writer = new FileWriter(config.getPathJsonMessages())) {
            gsonFile.toJson(mensajes, writer);
            return Either.right(true);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return Either.left(ErrorAppDataBase.NO_CONNECTION);
        }
    }
}
