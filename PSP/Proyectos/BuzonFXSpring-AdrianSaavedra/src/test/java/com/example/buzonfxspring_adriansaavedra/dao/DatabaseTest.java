package com.example.buzonfxspring_adriansaavedra.dao;


import com.example.buzonfxspring_adriansaavedra.common.Constantes;
import com.example.buzonfxspring_adriansaavedra.common.config.Configuracion;
import com.example.buzonfxspring_adriansaavedra.domain.errors.ErrorApp;
import com.example.buzonfxspring_adriansaavedra.domain.model.*;
import com.google.gson.*;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import nl.altindag.log.LogCaptor;
import nl.altindag.log.model.LogEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


import static org.junit.jupiter.api.Assertions.*;

@Log4j2
@ExtendWith(MockitoExtension.class)
class DatabaseTest {

    @InjectMocks
   private Configuracion config;
    private Database database;

    @BeforeEach
    void setUp() {
        Gson gson = new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) -> LocalDateTime.parse(json.getAsJsonPrimitive().getAsString()))
                .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (localDateTime, type, jsonSerializationContext) -> new JsonPrimitive(localDateTime.toString()))
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, type, jsonDeserializationContext) -> LocalDate.parse(json.getAsJsonPrimitive().getAsString()))
                .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (localDate, type, jsonSerializationContext) -> new JsonPrimitive(localDate.toString())
                ).create();
        database = new Database(config, gson);
        String[] filesToDelete = {
               config.getPathJsonUsuarios(),
                config.getPathJsonPublicGroup(),
                config.getPathJsonPrivateGroup(),
                config.getPathJsonPublicMessages(),
                config.getPathJsonPrivateMessages(),
        };

        for (String filePath : filesToDelete) {
            try {

                Files.deleteIfExists(Paths.get(filePath));

            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }


    @Test
    void loadUsuariosSuccess() {
        try {
            Files.copy(Paths.get(Constantes.PATH_JSON_USUARIOS_OK), Paths.get(config.getPathJsonUsuarios()));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        Either<ErrorApp, List<Usuario>> result = database.loadUsuarios();
        assertThat(result).hasSize(1);
    }

    @Test
    void loadUsuariosFail() {

        try (LogCaptor logCaptor = LogCaptor.forClass(Database.class)) {
            Either<ErrorApp, List<Usuario>> result = database.loadUsuarios();
            List<LogEvent> logEvents = logCaptor.getLogEvents();
            assertThat(logEvents).hasSize(1);
            LogEvent logEvent = logEvents.getFirst();
            assertThat(logEvent.getLevel()).isEqualTo(Constantes.ERROR);
            assertThat(logEvent.getThrowable()).isPresent();
            assertThat(result).isEmpty();
        }
    }

    @Test
    void saveUsuarios() {
        List<Usuario> usuarios = List.of(new Usuario("mateo"));
        Either<ErrorApp, Boolean> result = database.saveUsuarios(usuarios);
        assertTrue(result.isRight());
        assertTrue(result.get());
    }


    @Test
    void loadGruposPublicosSuccess() {
        try {
            Files.copy(Paths.get(Constantes.PATH_JSON_PUBLIC_GROUP_OK), Paths.get(config.getPathJsonPublicGroup()));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        Either<ErrorApp, List<GrupoPublico>> result = database.loadGruposPublicos();
        assertThat(result).hasSize(1);
    }

    @Test
    void loadGruposPublicosFail() {
        try (LogCaptor logCaptor = LogCaptor.forClass(Database.class)) {
            Either<ErrorApp, List<GrupoPublico>> result = database.loadGruposPublicos();
            List<LogEvent> logEvents = logCaptor.getLogEvents();
            assertThat(logEvents).hasSize(1);
            LogEvent logEvent = logEvents.getFirst();
            assertThat(logEvent.getLevel()).isEqualTo(Constantes.ERROR);
            assertThat(logEvent.getThrowable()).isPresent();
            assertThat(result).isEmpty();
        }
    }


    @Test
    void saveGruposPublicos() {
        List<GrupoPublico> grupoPublicos = List.of(new GrupoPublico("test", "test", new Usuario("test")));
        Either<ErrorApp, Boolean> result = database.saveGruposPublicos(grupoPublicos);
        assertTrue(result.isRight());
        assertTrue(result.get());

        assertThat(new File(config.getPathJsonPublicGroup())).exists();

    }


    @Test
    void loadGruposPrivadosSuccess() {
        try {
            Files.copy(Paths.get(Constantes.PATH_JSON_PRIVATE_GROUP_OK), Paths.get(config.getPathJsonPrivateGroup()));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        Either<ErrorApp, List<GrupoPrivado>> result = database.loadGruposPrivados();
        assertThat(result).hasSize(1);

    }

    @Test
    void loadGruposPrivadosFail() {
        try (LogCaptor logCaptor = LogCaptor.forClass(Database.class)) {
            Either<ErrorApp, List<GrupoPrivado>> result = database.loadGruposPrivados();
            List<LogEvent> logEvents = logCaptor.getLogEvents();
            assertThat(logEvents).hasSize(1);
            LogEvent logEvent = logEvents.getFirst();
            assertThat(logEvent.getLevel()).isEqualTo(Constantes.ERROR);
            assertThat(logEvent.getThrowable()).isPresent();
            assertThat(result).isEmpty();
        }

    }

    @Test
    void saveGruposPrivados() {
        List<GrupoPrivado> grupoPrivados = List.of(new GrupoPrivado("test", new Usuario("test")));
        Either<ErrorApp, Boolean> result = database.saveGruposPrivados(grupoPrivados);
        assertTrue(result.isRight());
        assertTrue(result.get());

        assertThat(new File(config.getPathJsonPrivateGroup())).exists();


    }


    @Test
    void loadMensajesPublicos() {
        try {
            Files.copy(Paths.get(Constantes.PATH_JSON_PUBLIC_MESSAGES_OK), Paths.get(config.getPathJsonPublicMessages()));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        Either<ErrorApp, List<MensajePublico>> result = database.loadMensajesPublicos();
        assertThat(result).hasSize(1);

    }




    @Test
    void saveMensajesPublicos() {
        List<MensajePublico> mensajePublicos = List.of(new MensajePublico("Test", LocalDateTime.now(), new Usuario("mateo"), List.of(new Usuario("pepito")), "prueba"));
        Either<ErrorApp, Boolean> result = database.saveMensajesPublicos(mensajePublicos);


        assertTrue(result.isRight());
        assertTrue(result.get());


        File file = new File(config.getPathJsonPublicMessages());
        assertThat(file).exists();
    }



    @Test
    void loadMensajesPrivados() {
        try {
            Files.copy(Paths.get(Constantes.PATH_JSON_PRIVATE_MESSAGES_OK), Paths.get(config.getPathJsonPrivateMessages()));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        Either<ErrorApp, List<MensajePrivado>> result = database.loadMensajesPrivados();
        assertThat(result).hasSize(1);
    }

}