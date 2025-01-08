package com.example.buzonfxspring_adriansaavedra.dao;


import com.example.buzonfxspring_adriansaavedra.common.config.Configuracion;
import com.example.buzonfxspring_adriansaavedra.domain.errors.ErrorApp;
import com.example.buzonfxspring_adriansaavedra.domain.errors.ErrorAppDataBase;
import com.example.buzonfxspring_adriansaavedra.domain.model.*;
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

    public Either<ErrorApp, List<GrupoPublico>> loadGruposPublicos() {
        Type userListType = new TypeToken<List<GrupoPublico>>() {
        }.getType();
        List<GrupoPublico> grupoPublicos;
        try {
            grupoPublicos = gsonFile.fromJson(
                    new FileReader(config.getPathJsonPublicGroup()),
                    userListType);
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
            return Either.left(ErrorAppDataBase.NO_CONNECTION);
        }
        return Either.right(grupoPublicos);
    }

    public Either<ErrorApp, Boolean> saveGruposPublicos(List<GrupoPublico> grupoPublicos) {
        String filePath = config.getPathJsonPublicGroup();
        try (FileWriter fw = new FileWriter(filePath)) {
            gsonFile.toJson(grupoPublicos, fw);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return Either.left(ErrorAppDataBase.NO_CONNECTION);
        }
        return Either.right(true);
    }



    public Either<ErrorApp, List<GrupoPrivado>> loadGruposPrivados() {
        String filePath = config.getPathJsonPrivateGroup();
        Type userListType = new TypeToken<List<GrupoPrivado>>() {
        }.getType();
        List<GrupoPrivado> grupoPrivados;
        try {
            grupoPrivados = gsonFile.fromJson(
                    new FileReader(filePath),
                    userListType);
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
            return Either.left(ErrorAppDataBase.NO_CONNECTION);
        }
        return Either.right(grupoPrivados);
    }

    public Either<ErrorApp, Boolean> saveGruposPrivados(List<GrupoPrivado> grupoPrivados) {
        String filePath = config.getPathJsonPrivateGroup();
        try (FileWriter fw = new FileWriter(filePath)) {
            gsonFile.toJson(grupoPrivados, fw);
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




    public Either<ErrorApp, List<MensajePublico>> loadMensajesPublicos() {
        Type userListType = new TypeToken<ArrayList<MensajePublico>>() {}.getType();
        try (FileReader reader = new FileReader(config.getPathJsonPublicMessages())) {
            List<MensajePublico> mensajePublicos = gsonFile.fromJson(reader, userListType);
            return Either.right(mensajePublicos != null ? mensajePublicos : new ArrayList<>());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return Either.left(ErrorAppDataBase.NO_CONNECTION);
        }
    }

    public Either<ErrorApp, Boolean> saveMensajesPublicos(List<MensajePublico> mensajePublicos) {
        try (FileWriter writer = new FileWriter(config.getPathJsonPublicMessages())) {
            gsonFile.toJson(mensajePublicos, writer);
            return Either.right(true);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return Either.left(ErrorAppDataBase.NO_CONNECTION);
        }
    }

    public Either<ErrorApp,List<MensajePrivado>> loadMensajesPrivados() {
        Type userListType = new TypeToken<ArrayList<MensajePrivado>>() {}.getType();
        try (FileReader reader = new FileReader(config.getPathJsonPrivateMessages())) {
            List<MensajePrivado> mensajePrivados = gsonFile.fromJson(reader, userListType);
            return Either.right(mensajePrivados != null ? mensajePrivados : new ArrayList<>());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return Either.left(ErrorAppDataBase.NO_CONNECTION);
        }
    }

    public Either<ErrorApp, Boolean> saveMensajesPrivados(List<MensajePrivado> mensajePrivados) {
        try (FileWriter writer = new FileWriter(config.getPathJsonPrivateMessages())) {
            gsonFile.toJson(mensajePrivados, writer);
            return Either.right(true);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return Either.left(ErrorAppDataBase.NO_CONNECTION);
        }
    }



}
