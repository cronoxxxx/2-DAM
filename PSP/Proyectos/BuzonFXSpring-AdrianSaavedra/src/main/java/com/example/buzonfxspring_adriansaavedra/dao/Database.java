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





    public Either<ErrorApp, List<Usuario>> loadUsuarios() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Type userListType = new TypeToken<ArrayList<Usuario>>() {}.getType();

        List<Usuario> usuarios;
        try {
            usuarios = gson.fromJson(
                    new FileReader(config.getPathJsonUsuarios()),
                    userListType);
        } catch (IOException e) {
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




    public Either<ErrorApp, List<GrupoPublico>> loadGruposPublicos() {
        Type userListType = new TypeToken<List<GrupoPublico>>() {
        }.getType();
        List<GrupoPublico> grupoPublicos;
        try {
            grupoPublicos = gsonFile.fromJson(
                    new FileReader(config.getPathJsonPublicGroup()),
                    userListType);
        } catch (IOException e) {
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
        } catch (IOException e) {
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





    public Either<ErrorApp, List<MensajePublico>> loadMensajesPublicos() {
        String filePath = config.getPathJsonPublicMessages();
        Type userListType = new TypeToken<ArrayList<MensajePublico>>() {
        }.getType();
        List<MensajePublico> mensajePublicos;
        try  {
            mensajePublicos = gsonFile.fromJson(
                    new FileReader(filePath),
                    userListType
            );
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return Either.left(ErrorAppDataBase.NO_CONNECTION);
        }
        return Either.right(mensajePublicos);
    }


    public Either<ErrorApp, Boolean> saveMensajesPublicos(List<MensajePublico> mensajePublicos) {
        String filePath = config.getPathJsonPublicMessages();
        try (FileWriter writer = new FileWriter(filePath)) {
            gsonFile.toJson(mensajePublicos, writer);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return Either.left(ErrorAppDataBase.NO_CONNECTION);
        }
        return Either.right(true);
    }


    public Either<ErrorApp,List<MensajePrivado>> loadMensajesPrivados() {
        String filePath = config.getPathJsonPrivateMessages();
        Type userListType = new TypeToken<ArrayList<MensajePrivado>>() {}.getType();

        List<MensajePrivado> mensajePrivados;
        try  {
            mensajePrivados = gsonFile.fromJson(
                    new FileReader(filePath),
                    userListType
            );
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return Either.left(ErrorAppDataBase.NO_CONNECTION);
        }
        return Either.right(mensajePrivados);
    }

    public Either<ErrorApp, Boolean> saveMensajesPrivados(List<MensajePrivado> mensajePrivados) {
        String filePath = config.getPathJsonPrivateMessages();
        try (FileWriter writer = new FileWriter(filePath)) {
            gsonFile.toJson(mensajePrivados, writer);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return Either.left(ErrorAppDataBase.NO_CONNECTION);
        }
        return Either.right(true);
    }


}
