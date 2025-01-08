package com.example.buzonfxspring_adriansaavedra.domain.service;

import com.example.buzonfxspring_adriansaavedra.common.Constantes;
import com.example.buzonfxspring_adriansaavedra.common.seguridad.EncriptacionAsim;
import com.example.buzonfxspring_adriansaavedra.dao.DaoUsuarios;
import com.example.buzonfxspring_adriansaavedra.domain.errors.ErrorApp;
import com.example.buzonfxspring_adriansaavedra.domain.errors.ErrorAppDatosNoValidos;
import com.example.buzonfxspring_adriansaavedra.domain.model.Usuario;
import com.example.buzonfxspring_adriansaavedra.domain.validators.UserValidator;
import io.vavr.control.Either;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.*;
import java.security.KeyPair;
import java.security.cert.X509Certificate;
import java.util.concurrent.CompletableFuture;
@ExtendWith(MockitoExtension.class)
class GestionUsuariosTest {

    @Mock
    private DaoUsuarios daoUsuarios;
    @Mock
    private EncriptacionAsim encriptacionAsim;
    @Mock
    private UserValidator userValidator;
    @InjectMocks
    private GestionUsuarios gestionUsuarios;

    @Nested
    @DisplayName("Login Tests")
    class LoginTests {
        @Test
        void loginSuccessful() {
            // Given
            Usuario usuario = new Usuario("testUser");
            String password = "testPassword";
            when(userValidator.validateUser(usuario)).thenReturn(Either.right(true));
            when(daoUsuarios.buscarUsuarioPorNombre(usuario.getNombre())).thenReturn(Either.right(usuario));
            when(encriptacionAsim.verificarCertificadoUsuario(usuario.getNombre(), password)).thenReturn(Either.right(true));
            // When
            CompletableFuture<Either<ErrorApp, Usuario>> result = gestionUsuarios.login(usuario, password);
            // Then
            assertThat(result.join().isRight()).isTrue();
            assertThat(result.join().get()).isEqualTo(usuario);
        }

        @Test
        void loginFailsWithInvalidUser() {
            // Given
            Usuario usuario = new Usuario("invalidUser");
            String password = "password";
            when(userValidator.validateUser(usuario)).thenReturn(Either.left(new ErrorAppDatosNoValidos(Constantes.USUARIO_INVALIDO)));

            // When
            CompletableFuture<Either<ErrorApp, Usuario>> result = gestionUsuarios.login(usuario, password);

            // Then
            assertThat(result.join().isLeft()).isTrue();
            assertThat(result.join().getLeft()).isInstanceOf(ErrorAppDatosNoValidos.class);
        }
    }

    @Nested
    @DisplayName("Register Tests")
    class RegisterTests {
        @Test
        void registerSuccessful() {
            // Given
            Usuario usuario = new Usuario("newUser");
            String password = "newPassword";
            KeyPair keyPair = mock(KeyPair.class);
            X509Certificate certificate = mock(X509Certificate.class);

            when(userValidator.validateUser(usuario)).thenReturn(Either.right(true));
            when(encriptacionAsim.generarParClaves()).thenReturn(Either.right(keyPair));
            when(encriptacionAsim.generarCertificadoUsuario(usuario.getNombre(), keyPair)).thenReturn(Either.right(certificate));
            when(encriptacionAsim.almacenarClavesUsuario(usuario.getNombre(), keyPair, certificate, password)).thenReturn(Either.right(true));
            when(daoUsuarios.addUsuario(usuario)).thenReturn(Either.right(true));

            // When
            CompletableFuture<Either<ErrorApp, Boolean>> result = gestionUsuarios.register(usuario, password);

            // Then
            assertThat(result.join().isRight()).isTrue();
            assertThat(result.join().get()).isTrue();
        }

        @Test
        void registerFailsWithInvalidUser() {
            // Given
            Usuario usuario = new Usuario("invalidUser");
            String password = "password";
            when(userValidator.validateUser(usuario)).thenReturn(Either.left(new ErrorAppDatosNoValidos(Constantes.USUARIO_INVALIDO)));

            // When
            CompletableFuture<Either<ErrorApp, Boolean>> result = gestionUsuarios.register(usuario, password);

            // Then
            assertThat(result.join().isLeft()).isTrue();
            assertThat(result.join().getLeft()).isInstanceOf(ErrorAppDatosNoValidos.class);
        }
    }

    @Nested
    @DisplayName("Buscar Usuario Tests")
    class BuscarUsuarioTests {
        @Test
        void findExistingUserByName() {
            // Given
            String nombre = "existingUser";
            Usuario usuario = new Usuario(nombre);
            when(daoUsuarios.buscarUsuarioPorNombre(nombre)).thenReturn(Either.right(usuario));

            // When
            CompletableFuture<Either<ErrorApp, Usuario>> result = gestionUsuarios.buscarUsuarioPorNombre(nombre);

            // Then
            assertThat(result.join().isRight()).isTrue();
            assertThat(result.join().get()).isEqualTo(usuario);
        }

        @Test
        void failToFindNonExistentUserByName() {
            // Given
            String nombre = "nonExistentUser";
            when(daoUsuarios.buscarUsuarioPorNombre(nombre)).thenReturn(Either.left(new ErrorAppDatosNoValidos(Constantes.USUARIO_NO_ENCONTRADO)));

            // When
            CompletableFuture<Either<ErrorApp, Usuario>> result = gestionUsuarios.buscarUsuarioPorNombre(nombre);

            // Then
            assertThat(result.join().isLeft()).isTrue();
            assertThat(result.join().getLeft()).isInstanceOf(ErrorAppDatosNoValidos.class);
        }
    }
}