package com.example.buzonfxspring_adriansaavedra.dao;

import com.example.buzonfxspring_adriansaavedra.common.Constantes;
import com.example.buzonfxspring_adriansaavedra.common.config.Configuracion;
import com.example.buzonfxspring_adriansaavedra.domain.errors.ErrorApp;
import com.example.buzonfxspring_adriansaavedra.domain.model.GrupoPrivado;
import com.example.buzonfxspring_adriansaavedra.domain.model.GrupoPublico;
import com.example.buzonfxspring_adriansaavedra.domain.model.Usuario;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@Log4j2
@ExtendWith(MockitoExtension.class)
class DaoGruposTest {

    @InjectMocks
    private Configuracion configuracion;

    @InjectMocks
    private DaoGrupos daoGrupos;

    @Mock
    private Database database;

    private Gson gson;

    @BeforeEach
    void setUp() {
        gson = new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) -> LocalDateTime.parse(json.getAsJsonPrimitive().getAsString()))
                .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (localDateTime, type, jsonSerializationContext) -> new JsonPrimitive(localDateTime.toString()))
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, type, jsonDeserializationContext) -> LocalDate.parse(json.getAsJsonPrimitive().getAsString()))
                .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (localDate, type, jsonSerializationContext) -> new JsonPrimitive(localDate.toString())
                ).create();
    }

    @Nested
    @DisplayName("Agregar nuevo grupo")
    class RegistrarGrupo {
        @Test
        void registrarGrupoPublico() {
            // Given
            GrupoPublico nuevoGrupo = new GrupoPublico("newGroup", "desc", new Usuario("owner"));
            List<GrupoPublico> grupos = loadGruposPublicosFromJson(configuracion.getPathJsonPublicGroup());
            when(database.loadGruposPublicos()).thenReturn(Either.right(grupos));
            when(database.saveGruposPublicos(anyList())).thenReturn(Either.right(true));

            // When
            Either<ErrorApp, Boolean> result = daoGrupos.registrarGrupoPublico(nuevoGrupo);

            // Then
            assertThat(result.isRight()).isTrue();
            assertThat(result.get()).isTrue();
            assertThat(grupos).contains(nuevoGrupo);
            verify(database).saveGruposPublicos(grupos);
        }

        @Test
        void registrarGrupoPrivado() {
            // Given
            GrupoPrivado nuevoGrupo = new GrupoPrivado("hola", new Usuario("ble"));
            List<GrupoPrivado> grupos = loadGruposPrivadosFromJson(configuracion.getPathJsonPrivateGroup());
            when(database.loadGruposPrivados()).thenReturn(Either.right(grupos));
            when(database.saveGruposPrivados(anyList())).thenReturn(Either.right(true));

            // When
            Either<ErrorApp, Boolean> result = daoGrupos.registrarGrupoPrivado(nuevoGrupo);

            // Then
            assertThat(result.isRight()).isTrue();
            assertThat(result.get()).isTrue();
            assertThat(grupos).contains(nuevoGrupo);
            verify(database).saveGruposPrivados(grupos);
        }
    }


    @Nested
    @DisplayName("Obtener grupo por nombres")
    class ObtenerGrupoPorNombre {
        @Test
        void obtenerGrupoPublicoPorNombre() {
            // Given
            List<GrupoPublico> grupos = loadGruposPublicosFromJson(configuracion.getPathJsonPublicGroup());
            when(database.loadGruposPublicos()).thenReturn(Either.right(grupos));
            // When
            Either<ErrorApp, GrupoPublico> result = daoGrupos.obtenerGrupoPublicoPorNombre(Constantes.PUBLICO);
            // Then
            assertThat(result.isRight()).isTrue();
            assertThat(grupos).hasSize(1);
            assertThat(result.get()).isEqualTo(grupos.getFirst());
        }

        @Test
        void obtenerGrupoPrivadoPorNombre() {
            // Given
            List<GrupoPrivado> grupos = loadGruposPrivadosFromJson(configuracion.getPathJsonPrivateGroup());
            when(database.loadGruposPrivados()).thenReturn(Either.right(grupos));

            // When
            Either<ErrorApp, GrupoPrivado> result = daoGrupos.obtenerGrupoPrivadoPorNombre(Constantes.SECRETO);

            // Then
            assertThat(result.isRight()).isTrue();
            assertThat(grupos).hasSize(1);
            assertThat(result.get()).isEqualTo(grupos.getFirst());
        }
    }

    @Nested
    @DisplayName("Agregar miembro a un grupo")
    class AgregarMiembroGrupo {
        @Test
        void agregarMiembroGrupoPublico() {
            // Given
            List<GrupoPublico> grupos = loadGruposPublicosFromJson(configuracion.getPathJsonPublicGroup());
            GrupoPublico grupoExistente = grupos.stream()
                    .filter(g -> g.getNombre().equals("publico"))
                    .findFirst().orElse(null);

            Usuario nuevoMiembro = new Usuario("juanca");

            when(database.loadGruposPublicos()).thenReturn(Either.right(grupos));
            when(database.saveGruposPublicos(anyList())).thenReturn(Either.right(true));

            // When
            Either<ErrorApp, Boolean> result = daoGrupos.agregarMiembroGrupoPublico(grupoExistente, nuevoMiembro);

            // Then
            assertThat(result.isRight()).isTrue();
            assertThat(result.get()).isTrue();
            assertThat(grupoExistente).isNotNull();
            assertThat(grupoExistente.getParticipantes()).contains(nuevoMiembro);
            verify(database).saveGruposPublicos(grupos);
        }

        @Test
        void agregarMiembroGrupoPrivado() {
            // Given
            List<GrupoPrivado> grupos = loadGruposPrivadosFromJson(configuracion.getPathJsonPrivateGroup());
            GrupoPrivado grupoExistente = grupos.stream()
                    .filter(g -> g.getNombre().equals("secreto"))
                    .findFirst().orElse(null);

            Usuario nuevoMiembro = new Usuario("juanca");

            when(database.loadGruposPrivados()).thenReturn(Either.right(grupos));
            when(database.saveGruposPrivados(anyList())).thenReturn(Either.right(true));

            // When
            Either<ErrorApp, Boolean> result = daoGrupos.agregarMiembroGrupoPrivado(grupoExistente, nuevoMiembro);

            // Then
            assertThat(result.isRight()).isTrue();
            assertThat(result.get()).isTrue();
            assertThat(grupoExistente).isNotNull();
            assertThat(grupoExistente.getParticipantes()).contains(nuevoMiembro);
            verify(database).saveGruposPrivados(grupos);
        }
    }

    private List<GrupoPublico> loadGruposPublicosFromJson(String fileName) {
        Type grupoListType = new TypeToken<ArrayList<GrupoPublico>>() {
        }.getType();
        try (FileReader reader = new FileReader(fileName)) {
            return gson.fromJson(reader, grupoListType);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    private List<GrupoPrivado> loadGruposPrivadosFromJson(String fileName) {
        Type grupoListType = new TypeToken<ArrayList<GrupoPrivado>>() {
        }.getType();
        try (FileReader reader = new FileReader(fileName)) {
            return gson.fromJson(reader, grupoListType);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }
}