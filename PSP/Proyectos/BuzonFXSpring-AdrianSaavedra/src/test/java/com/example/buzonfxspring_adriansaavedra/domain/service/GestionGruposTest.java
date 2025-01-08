package com.example.buzonfxspring_adriansaavedra.domain.service;

import com.example.buzonfxspring_adriansaavedra.common.Constantes;
import com.example.buzonfxspring_adriansaavedra.dao.DaoGrupos;
import com.example.buzonfxspring_adriansaavedra.domain.errors.ErrorApp;
import com.example.buzonfxspring_adriansaavedra.domain.errors.ErrorAppDatosNoValidos;
import com.example.buzonfxspring_adriansaavedra.domain.model.GrupoPrivado;
import com.example.buzonfxspring_adriansaavedra.domain.model.GrupoPublico;
import com.example.buzonfxspring_adriansaavedra.domain.model.Usuario;
import com.example.buzonfxspring_adriansaavedra.domain.validators.GrupoPrivadoValidator;
import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@Log4j2
@ExtendWith(MockitoExtension.class)
class GestionGruposTest {

    @InjectMocks
    private GestionGrupos gestionGrupos;
    @Mock
    private DaoGrupos daoGrupos;
    @Mock
    private GrupoPrivadoValidator grupoPrivadoValidator;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Nested
    class LoginGrupoPublicoTests {
        @Test
        void loginGrupoPublicoSuccessful() {
            // Given
            String nombreGrupo = "testGrupo";
            String password = "testPassword";
            Usuario usuario = new Usuario("testUser");
            GrupoPublico grupo = new GrupoPublico(nombreGrupo, "desc", new Usuario("owner"));
            grupo.setPassword("encodedPassword");
            grupo.getParticipantes().add(usuario);

            when(daoGrupos.obtenerGrupoPublicoPorNombre(nombreGrupo)).thenReturn(Either.right(grupo));
            when(passwordEncoder.matches(password, grupo.getPassword())).thenReturn(true);

            // When
            CompletableFuture<Either<ErrorApp, GrupoPublico>> result = gestionGrupos.loginGrupoPublico(nombreGrupo, password, usuario);

            // Then
            assertThat(result.join().isRight()).isTrue();
            assertThat(result.join().get()).isEqualTo(grupo);
        }

        @Test
        void loginGrupoPublicoNewMember() {
            // Given
            String nombreGrupo = "testGrupo";
            String password = "testPassword";
            Usuario usuario = new Usuario("testUser");
            GrupoPublico grupo = new GrupoPublico(nombreGrupo, "desc", new Usuario("owner"));
            grupo.setPassword("encodedPassword");

            when(daoGrupos.obtenerGrupoPublicoPorNombre(nombreGrupo)).thenReturn(Either.right(grupo));
            when(passwordEncoder.matches(password, grupo.getPassword())).thenReturn(true);
            when(daoGrupos.agregarMiembroGrupoPublico(grupo, usuario)).thenReturn(Either.right(true));

            // When
            CompletableFuture<Either<ErrorApp, GrupoPublico>> result = gestionGrupos.loginGrupoPublico(nombreGrupo, password, usuario);

            // Then
            assertThat(result.join().isRight()).isTrue();
            assertThat(result.join().get()).isEqualTo(grupo);
            verify(daoGrupos).agregarMiembroGrupoPublico(grupo, usuario);
        }

        @Test
        void loginGrupoPublicoWrongPassword() {
            // Given
            String nombreGrupo = "testGrupo";
            String password = "wrongPassword";
            Usuario usuario = new Usuario("testUser");
            GrupoPublico grupo = new GrupoPublico(nombreGrupo, "desc", new Usuario("owner"));
            grupo.setPassword("encodedPassword");

            when(daoGrupos.obtenerGrupoPublicoPorNombre(nombreGrupo)).thenReturn(Either.right(grupo));
            when(passwordEncoder.matches(password, grupo.getPassword())).thenReturn(false);

            // When
            CompletableFuture<Either<ErrorApp, GrupoPublico>> result = gestionGrupos.loginGrupoPublico(nombreGrupo, password, usuario);

            // Then
            assertThat(result.join().isLeft()).isTrue();
            assertThat(result.join().getLeft()).isInstanceOf(ErrorAppDatosNoValidos.class);
        }
    }

    @Nested
    class AgregarMiembroGrupoPrivadoTests {
        @Test
        void agregarMiembroGrupoPrivadoSuccessful() {
            // Given
            GrupoPrivado grupo = new GrupoPrivado("testGrupo", new Usuario("owner"));
            Usuario miembro = new Usuario("newMember");

            when(grupoPrivadoValidator.validateGrupo(grupo)).thenReturn(Either.right(true));
            when(daoGrupos.agregarMiembroGrupoPrivado(grupo, miembro)).thenReturn(Either.right(true));

            // When
            CompletableFuture<Either<ErrorApp, Boolean>> result = gestionGrupos.agregarMiembroGrupoPrivado(grupo, miembro);

            // Then
            assertThat(result.join().isRight()).isTrue();
            assertThat(result.join().get()).isTrue();
        }

        @Test
        void agregarMiembroGrupoPrivadoValidationFails() {
            // Given
            GrupoPrivado grupo = new GrupoPrivado("testGrupo", new Usuario("owner"));
            Usuario miembro = new Usuario("newMember");

            when(grupoPrivadoValidator.validateGrupo(grupo)).thenReturn(Either.left(new ErrorAppDatosNoValidos(Constantes.GRUPO_INVALIDO)));

            // When
            CompletableFuture<Either<ErrorApp, Boolean>> result = gestionGrupos.agregarMiembroGrupoPrivado(grupo, miembro);

            // Then
            assertThat(result.join().isLeft()).isTrue();
            assertThat(result.join().getLeft()).isInstanceOf(ErrorAppDatosNoValidos.class);
        }
    }
}