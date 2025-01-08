package com.example.buzonfxspring_adriansaavedra.domain.service;

import com.example.buzonfxspring_adriansaavedra.common.Constantes;
import com.example.buzonfxspring_adriansaavedra.common.seguridad.EncriptacionAES;
import com.example.buzonfxspring_adriansaavedra.common.seguridad.EncriptacionAsim;
import com.example.buzonfxspring_adriansaavedra.dao.DaoMensajes;
import com.example.buzonfxspring_adriansaavedra.domain.errors.ErrorApp;
import com.example.buzonfxspring_adriansaavedra.domain.model.*;
import io.vavr.control.Either;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;



import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.security.KeyPair;
import java.security.PublicKey;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@ExtendWith(MockitoExtension.class)
class GestionMensajesTest {

    @Mock
    private DaoMensajes daoMensajes;
    @Mock
    private EncriptacionAES encriptacionAES;
    @Mock
    private EncriptacionAsim encriptacionAsim;
    @InjectMocks
    private GestionMensajes gestionMensajes;

    @Test
    void obtenerYDesencriptarMensajesPublicos() {
        // Given
        GrupoPublico grupoPublico = new GrupoPublico(Constantes.PUBLICO, "desc", new Usuario("ble"));
        grupoPublico.setPassword(Constantes.PASSWORD);
        MensajePublico mensajePublico = new MensajePublico(Constantes.MENSAJE_ENCRIPTADO, LocalDateTime.now(), new Usuario("ble"), List.of(new Usuario("ble")), Constantes.PUBLICO);
        mensajePublico.setFirma("firma");
        PublicKey mockPublicKey = mock(PublicKey.class);

        when(daoMensajes.obtenerMensajesDeGrupoPublico(grupoPublico)).thenReturn(Either.right(List.of(mensajePublico)));
        when(encriptacionAES.desencriptar(Constantes.MENSAJE_ENCRIPTADO, Constantes.PASSWORD)).thenReturn(Either.right(Constantes.MENSAJE_DESENCRIPTADO));
        when(encriptacionAsim.obtenerClavePublica("ble")).thenReturn(Either.right(mockPublicKey));
        when(encriptacionAsim.verificarFirma(Constantes.MENSAJE_DESENCRIPTADO, Constantes.FIRMA, mockPublicKey)).thenReturn(Either.right(true));

        // When
        CompletableFuture<Either<ErrorApp, List<MensajePublico>>> result = gestionMensajes.obtenerYDesencriptarMensajesPublicos(grupoPublico);

        // Then
        assertThat(result.join().isRight()).isTrue();
        assertThat(result.join().get()).hasSize(1);
        assertThat(result.join().get().getFirst().getTexto()).isEqualTo(Constantes.MENSAJE_DESENCRIPTADO);
    }

    @Test
    void addMensajePublico() {
        // Given
        GrupoPublico grupoPublico = new GrupoPublico(Constantes.PUBLICO, "desc", new Usuario("ble"));
        grupoPublico.setPassword(Constantes.PASSWORD);
        MensajePublico mensajePublico = new MensajePublico(Constantes.NUEVO_MENSAJE, LocalDateTime.now(), new Usuario("ble"), List.of(new Usuario("ble")), Constantes.PUBLICO);
        KeyPair keyPair = mock(KeyPair.class);

        when(encriptacionAsim.obtenerParClaves("ble", "userPassword")).thenReturn(Either.right(keyPair));
        when(encriptacionAES.encriptar(Constantes.NUEVO_MENSAJE, Constantes.PASSWORD)).thenReturn(Either.right(Constantes.MENSAJE_ENCRIPTADO));
        when(encriptacionAsim.firmarMensaje(Constantes.NUEVO_MENSAJE, keyPair.getPrivate())).thenReturn(Either.right(Constantes.FIRMA));
        when(daoMensajes.addMensajesPublicos(mensajePublico)).thenReturn(Either.right(true));

        // When
        CompletableFuture<Either<ErrorApp, Boolean>> result = gestionMensajes.addMensajePublico(mensajePublico, grupoPublico, "userPassword");

        // Then
        assertThat(result.join().isRight()).isTrue();
        assertThat(result.join().get()).isTrue();
    }

    @Test
    void addMensajePrivado() {
        // Given
        MensajePrivado mensajePrivado = new MensajePrivado(Constantes.NUEVO_MENSAJE, LocalDateTime.now(), new Usuario("ble"), List.of(new Usuario(Constantes.RECEPTOR)), "secreto");
        KeyPair keyPair = mock(KeyPair.class);
        PublicKey publicKey = mock(PublicKey.class);

        when(encriptacionAsim.generarClaveSimetricaAleatoria()).thenReturn(Either.right("claveSimetrica"));
        when(encriptacionAES.encriptar(Constantes.NUEVO_MENSAJE, "claveSimetrica")).thenReturn(Either.right(Constantes.MENSAJE_ENCRIPTADO));
        when(encriptacionAsim.obtenerParClaves("ble", Constantes.PASSWORD)).thenReturn(Either.right(keyPair));
        when(encriptacionAsim.firmarMensaje(Constantes.NUEVO_MENSAJE, keyPair.getPrivate())).thenReturn(Either.right(Constantes.FIRMA));
        when(encriptacionAsim.obtenerClavePublica(Constantes.RECEPTOR)).thenReturn(Either.right(publicKey));
        when(encriptacionAsim.encriptar("claveSimetrica", publicKey)).thenReturn(Either.right(Constantes.CLAVE_CIFRADA));
        when(daoMensajes.addMensajesPrivados(mensajePrivado)).thenReturn(Either.right(true));

        // When
        CompletableFuture<Either<ErrorApp, Boolean>> result = gestionMensajes.addMensajePrivado(mensajePrivado, Constantes.PASSWORD);

        // Then
        assertThat(result.join().isRight()).isTrue();
        assertThat(result.join().get()).isTrue();
    }

    @Test
    void obtenerYDesencriptarMensajesPrivados() {
        // Given
        GrupoPrivado grupoPrivado = new GrupoPrivado(Constantes.SECRETO, new Usuario("ble"));
        Usuario receptor = new Usuario(Constantes.RECEPTOR);
        MensajePrivado mensajePrivado = new MensajePrivado(Constantes.MENSAJE_ENCRIPTADO, LocalDateTime.now(), new Usuario("ble"), List.of(receptor), Constantes.SECRETO);
        mensajePrivado.setFirma(Constantes.FIRMA);
        mensajePrivado.setMensajesPrivadosUserClave(List.of(new MensajePrivadoUserClave(Constantes.RECEPTOR, Constantes.CLAVE_CIFRADA)));

        KeyPair keyPair = mock(KeyPair.class);
        PublicKey publicKey = mock(PublicKey.class);

        when(daoMensajes.obtenerMensajesDeGrupoPrivado(grupoPrivado)).thenReturn(Either.right(List.of(mensajePrivado)));
        when(encriptacionAsim.obtenerParClaves(Constantes.RECEPTOR, "userPassword")).thenReturn(Either.right(keyPair));
        when(encriptacionAsim.desencriptar(Constantes.CLAVE_CIFRADA, keyPair.getPrivate())).thenReturn(Either.right( Constantes.CLAVE_SIMETRICA));
        when(encriptacionAES.desencriptar(Constantes.MENSAJE_ENCRIPTADO, Constantes.CLAVE_SIMETRICA)).thenReturn(Either.right(Constantes.MENSAJE_DESENCRIPTADO));
        when(encriptacionAsim.obtenerClavePublica("ble")).thenReturn(Either.right(publicKey));
        when(encriptacionAsim.verificarFirma(Constantes.MENSAJE_DESENCRIPTADO, Constantes.FIRMA, publicKey)).thenReturn(Either.right(true));

        // When
        CompletableFuture<Either<ErrorApp, List<MensajePrivado>>> result = gestionMensajes.obtenerYDesencriptarMensajesPrivados(grupoPrivado, receptor, "userPassword");

        // Then
        assertThat(result.join().isRight()).isTrue();
        assertThat(result.join().get()).hasSize(1);
        assertThat(result.join().get().getFirst().getTexto()).isEqualTo(Constantes.MENSAJE_DESENCRIPTADO);
    }
}