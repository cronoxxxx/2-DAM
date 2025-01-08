package com.example.buzonfxspring_adriansaavedra.dao;

import static org.mockito.ArgumentMatchers.any;


import com.example.buzonfxspring_adriansaavedra.common.Constantes;
import com.example.buzonfxspring_adriansaavedra.common.config.Configuracion;
import com.example.buzonfxspring_adriansaavedra.domain.errors.ErrorApp;
import com.example.buzonfxspring_adriansaavedra.domain.errors.ErrorAppDatosNoValidos;
import com.example.buzonfxspring_adriansaavedra.domain.model.Usuario;


import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileReader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@Log4j2
@ExtendWith(MockitoExtension.class)
class DaoUsuariosTest {
    @InjectMocks
    private Configuracion configuracion;
    @InjectMocks
    private DaoUsuarios daoUsuarios;
    @Mock
    private Database database;

    private Gson gson;

    @BeforeEach
    void setUp() {
        gson = new GsonBuilder().create();
    }

    @Test

    void userEncontrado() {
        Usuario usuario = new Usuario("ble");
        List<Usuario> usuarios = loadUsuariosFromJson(configuracion.getPathJsonUsuarios());
        when(database.loadUsuarios()).thenReturn(Either.right(usuarios));

        Either<ErrorApp, Usuario> resultado = daoUsuarios.buscarUsuarioPorNombre(usuario.getNombre());

        assertThat(resultado).isEqualTo(Either.right(usuario));
    }

    @Test

    void userNoEncontrado() {
        List<Usuario> usuarios = loadUsuariosFromJson(configuracion.getPathJsonUsuarios());
        when(database.loadUsuarios()).thenReturn(Either.right(usuarios));

        Either<ErrorApp, Usuario> resultado = daoUsuarios.buscarUsuarioPorNombre(Constantes.ECIES);

        assertThat(resultado).isEqualTo(Either.left(new ErrorAppDatosNoValidos(Constantes.USUARIO_NO_ENCONTRADO)));
    }

    @Test

    void addNewUser() {
        Usuario usuario = new Usuario("pablito");
        List<Usuario> usuarios = loadUsuariosFromJson(configuracion.getPathJsonUsuarios());
        when(database.loadUsuarios()).thenReturn(Either.right(usuarios));
        when(database.saveUsuarios(any())).thenReturn(Either.right(true));

        Either<ErrorApp, Boolean> resultado = daoUsuarios.addUsuario(usuario);

        assertThat(resultado).isEqualTo(Either.right(true));
        assertThat(usuarios).contains(usuario);
    }

    @Test

    void agregarUserPeroYaExiste() {
        Usuario usuario = new Usuario("ble");
        List<Usuario> usuarios = loadUsuariosFromJson(configuracion.getPathJsonUsuarios());
        when(database.loadUsuarios()).thenReturn(Either.right(usuarios));

        Either<ErrorApp, Boolean> resultado = daoUsuarios.addUsuario(usuario);

        assertThat(resultado).isEqualTo(Either.left(new ErrorAppDatosNoValidos(Constantes.USUARIO_YA_EXISTE)));
    }

    private List<Usuario> loadUsuariosFromJson(String fileName) {
        Type userListType = new TypeToken<ArrayList<Usuario>>() {
        }.getType();
        try {
            return gson.fromJson(
                    new FileReader(fileName),
                    userListType);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }
}