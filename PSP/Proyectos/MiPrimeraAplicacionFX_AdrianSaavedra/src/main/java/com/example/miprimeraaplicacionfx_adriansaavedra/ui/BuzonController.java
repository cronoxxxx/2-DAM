package com.example.miprimeraaplicacionfx_adriansaavedra.ui;


import com.example.miprimeraaplicacionfx_adriansaavedra.domain.model.*;
import com.example.miprimeraaplicacionfx_adriansaavedra.common.Constantes;
import com.example.miprimeraaplicacionfx_adriansaavedra.domain.service.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;


public class BuzonController implements Initializable {

    @FXML
    private ListView<String> lvGruposDeUsuario;
    @FXML
    private ListView<Mensaje> lvMensajesGrupo;
    @FXML
    private TextField tfNewGroup;
    @FXML
    private TextField tfnewPasswordGroup;
    @FXML
    private Button btnRegisterPublicGroup;
    @FXML
    private Button btnRegisterSecretGroup;
    @FXML
    private TextField tfGroup;
    @FXML
    private Button btnIniciarGroup;
    @FXML
    private TextField tfPasswordGroup;
    @FXML
    private ListView<String> lvGrupoParticipantes;
    @FXML
    private TextArea taContenidoEnviar;
    @FXML
    private ListView<Mensaje> lvMensajesRecibidosUser;
    @FXML
    private ListView<String> lvGruposSecretos;
    @FXML
    private TextField tfAgregarUsuarioGrupo;
    @FXML
    private TextField tfNewUser;
    @FXML
    private TextField tfPasswordUser;
    @FXML
    private TextField tfUser;
    @FXML
    private Button btnRegisterUser;
    @FXML
    private Button btnIniciarUser;

    @FXML
    private TextField tfnewPasswordUser;
    private final GestionUsuarios gestionUsuarios;
    private final GestionGrupos gestionGrupos;
    private final GestionMensajes gestionMensajes;
    private Usuario usuario;
    private Grupo grupoActual;

    public BuzonController() {
        this.gestionUsuarios = new GestionUsuarios();
        this.gestionGrupos = new GestionGrupos();
        this.gestionMensajes = new GestionMensajes();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Arrays.asList(lvMensajesRecibidosUser, lvGruposSecretos, lvMensajesGrupo, lvGruposDeUsuario)
                .forEach(lv -> lv.setItems(FXCollections.observableArrayList()));
        lvGrupoParticipantes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        setupGroupListeners();

    }

    private void setupGroupListeners() {
        lvGruposDeUsuario.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                cargarGrupo(newValue);
                lvGruposSecretos.getSelectionModel().clearSelection();
            }
        });

        lvGruposSecretos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                cargarGrupo(newValue);
                lvGruposDeUsuario.getSelectionModel().clearSelection();
            }
        });
    }

    private void cargarGrupo(String nombreGrupo) {
        Grupo grupoSeleccionado = gestionGrupos.obtenerGrupoPorNombre(nombreGrupo);
        if (grupoSeleccionado != null) {
            grupoActual = grupoSeleccionado;
            actualizarVistaGrupo();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, Constantes.TITULO_ERROR_GRUPO_NO_ENCONTRADO, Constantes.CONTENIDO_ERROR_GRUPO_NO_ENCONTRADO);
        }
    }


    public void registrarUser() { //done

        if (tfNewUser.getText().isEmpty() || tfnewPasswordUser.getText().isEmpty()) {
            mostrarAlerta(Alert.AlertType.ERROR, Constantes.TITULO_ERROR_LOGIN, Constantes.CONTENIDO_ERROR_LOGIN);
        } else {
            Usuario registro = new Usuario(tfNewUser.getText(), tfnewPasswordUser.getText());
            gestionUsuarios.addUsuario(registro);
            if (gestionUsuarios.saveUsuarios(gestionUsuarios.obtenerUsuarios())) {
                mostrarAlerta(Alert.AlertType.CONFIRMATION, Constantes.TITULO_USUARIO_REGISTRADO, Constantes.CONTENIDO_USUARIO_REGISTRADO_CORRECTAMENTE + tfNewUser.getText());

            } else {
                mostrarAlerta(Alert.AlertType.ERROR, Constantes.TITULO_ERROR_REGISTRO_USUARIO, Constantes.CONTENIDO_ERROR_REGISTRO_USUARIO);
            }
        }

    }

    public void comprobarUser() {

        if (tfUser.getText().isEmpty() || tfPasswordUser.getText().isEmpty()) {
            mostrarAlerta(Alert.AlertType.ERROR, Constantes.TITULO_ERROR_LOGIN, Constantes.CONTENIDO_ERROR_LOGIN);
        } else {
            Usuario x = new Usuario(tfUser.getText(), tfPasswordUser.getText());
            Usuario existe = gestionUsuarios.verificacion(x);

            if (existe != null) {
                usuario = existe;
                List<Mensaje> mensajesUsuario = gestionMensajes.obtenerMensajesParaUsuario(usuario);
                lvMensajesRecibidosUser.getItems().addAll(mensajesUsuario);

                List<String> gruposPublicos = gestionGrupos.obtenerGruposParaUsuario(usuario.getNombre(), true);
                lvGruposDeUsuario.getItems().addAll(gruposPublicos);

                List<String> gruposSecretos = gestionGrupos.obtenerGruposParaUsuario(usuario.getNombre(), false);
                lvGruposSecretos.getItems().addAll(gruposSecretos);
                gestionUsuarios.actualizarUsuario(usuario);
                tfUser.setEditable(false);
                tfPasswordUser.setEditable(false);
                tfnewPasswordUser.setEditable(false);
                tfNewUser.setEditable(false);
                btnRegisterUser.setDisable(true);
                btnIniciarUser.setDisable(true);

                mostrarAlerta(Alert.AlertType.INFORMATION, Constantes.TITULO_EXITO_INICIO_SESION, Constantes.CONTENIDO_EXITO_INICIO_SESION + usuario.getNombre());
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, Constantes.TITULO_ERROR_LOGIN, Constantes.CONTENIDO_ERROR_LOGIN);
            }


        }


    }

    public void accederGrupoPublico() {
        if (tfGroup.getText() == null || tfGroup.getText().isEmpty() ||
                tfPasswordGroup.getText() == null || tfPasswordGroup.getText().isEmpty()) {
            mostrarAlerta(Alert.AlertType.ERROR, Constantes.TITULO_ERROR_CAMPO_VACIO, Constantes.CONTENIDO_ERROR_CAMPO_VACIO);
            return;
        }
        Grupo x = new Grupo(tfGroup.getText(), tfPasswordGroup.getText(), usuario, true);

        Grupo comprobar = gestionGrupos.ingresar(x);
        if (comprobar != null) {
            grupoActual = comprobar;

            tfGroup.setEditable(false);
            tfPasswordGroup.setEditable(false);
            btnRegisterSecretGroup.setDisable(true);
            btnRegisterPublicGroup.setDisable(true);
            btnIniciarGroup.setDisable(true);

            if (usuario != null) {
                gestionGrupos.agregarMiembroGrupo(comprobar, usuario);
            }

            actualizarListaParticipantes(comprobar);
            actualizarListaGrupos(comprobar);
            actualizarListaMensajes(comprobar);

            gestionGrupos.actualizarGrupo(comprobar);
            actualizarVistaGrupo();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, Constantes.TITULO_ERROR_GRUPO_NO_ENCONTRADO, Constantes.CONTENIDO_ERROR_GRUPO_NO_ENCONTRADO);
        }
    }

    public void registrarPublicGroup() {
        if (tfNewGroup.getText().isEmpty() || tfnewPasswordGroup.getText().isEmpty()) {
            mostrarAlerta(Alert.AlertType.ERROR, Constantes.TITULO_ERROR_GRUPO_NO_ENCONTRADO, Constantes.CONTENIDO_ERROR_GRUPO_NO_ENCONTRADO);
        } else {
            Grupo grupo = new Grupo(tfNewGroup.getText(), tfnewPasswordGroup.getText(), usuario, true);
            gestionGrupos.addGroup(grupo);
            if (gestionGrupos.saveGrupos(gestionGrupos.obtenerGrupos())) {
                mostrarAlerta(Alert.AlertType.CONFIRMATION, Constantes.TITULO_EXITO_GRUPO_REGISTRADO, Constantes.CONTENIDO_EXITO_GRUPO_REGISTRADO);
                lvGruposDeUsuario.getItems().add(grupo.getNombre());
            }
        }


    }

    public void registrarSecretGroup() {
        if (tfNewGroup.getText().isEmpty() || tfnewPasswordGroup.getText().isEmpty()) {
            mostrarAlerta(Alert.AlertType.ERROR, Constantes.TITULO_ERROR_GRUPO_NO_ENCONTRADO, Constantes.CONTENIDO_ERROR_GRUPO_NO_ENCONTRADO);
        } else {
            Grupo grupo = new Grupo(tfNewGroup.getText(), tfnewPasswordGroup.getText(), usuario, false);
            if (gestionGrupos.addGroup(grupo)) {
                mostrarAlerta(Alert.AlertType.CONFIRMATION, Constantes.TITULO_EXITO_GRUPO_REGISTRADO, Constantes.CONTENIDO_EXITO_GRUPO_REGISTRADO);
                lvGruposSecretos.getItems().add(grupo.getNombre());
            }


        }
    }

    public void enviarMensajeGrupos() {
        if (grupoActual != null && !taContenidoEnviar.getText().isEmpty()) {
            Mensaje mensaje = new Mensaje(taContenidoEnviar.getText(), LocalDateTime.now(), usuario, Collections.emptyList(), grupoActual.getNombre());
            if (gestionMensajes.addMensajes(mensaje)) {
                lvMensajesGrupo.getItems().add(mensaje);
                lvMensajesGrupo.scrollTo(lvMensajesGrupo.getItems().size() - 1);
                taContenidoEnviar.clear(); // Clear the text field after sending
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, Constantes.TITULO_ERROR_ENVIO_MENSAJE, Constantes.CONTENIDO_ERROR_ENVIO_MENSAJE);
            }
        }
    }

    public void agregarUsuarioAGrupo() {
        if (sonDatosValidos()) {
            if (esAdministradorDelGrupo()) {
                Usuario nuevoMiembro = gestionUsuarios.buscarUsuarioPorNombre(tfAgregarUsuarioGrupo.getText());
                if (nuevoMiembro != null) {
                    agregarMiembroSiNoExiste(nuevoMiembro);
                } else {
                    mostrarAlerta(Alert.AlertType.ERROR, Constantes.TITULO_USUARIO_NO_ENCONTRADO, Constantes.CONTENIDO_USUARIO_NO_ENCONTRADO);
                }
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, Constantes.TITULO_PERMISO_DENEGADO, Constantes.CONTENIDO_PERMISO_DENEGADO);
            }
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, Constantes.TITULO_ERROR_CAMPO_VACIO, Constantes.CONTENIDO_ERROR_CAMPO_VACIO);
        }
    }

    public void enviarMensajePersonas() {
        List<String> destinatarios = new ArrayList<>(lvGrupoParticipantes.getSelectionModel().getSelectedItems());

        if (!taContenidoEnviar.getText().isEmpty() && !destinatarios.isEmpty()) {
            List<Usuario> existentes = gestionUsuarios.buscarUsuariosPorNombres(destinatarios);

            if (!existentes.isEmpty()) {
                Mensaje mensaje = new Mensaje(taContenidoEnviar.getText(), LocalDateTime.now(), usuario, existentes, null);
                gestionMensajes.addMensajes(mensaje);

                if (gestionMensajes.saveMensajes(gestionMensajes.obtenerMensajes())) {
                    mostrarAlerta(Alert.AlertType.CONFIRMATION, Constantes.TITULO_EXITO_ENVIO_MENSAJE, Constantes.CONTENIDO_EXITO_ENVIO_MENSAJE);
                    taContenidoEnviar.clear(); // Limpiar el campo de texto después de enviar
                } else {
                    mostrarAlerta(Alert.AlertType.ERROR, Constantes.TITULO_ERROR_ENVIO_MENSAJE, Constantes.CONTENIDO_ERROR_ENVIO_MENSAJE);
                }
            }
        }
    }


    private void actualizarListaParticipantes(Grupo grupo) {
        lvGrupoParticipantes.getItems().clear();
        if (grupo.getParticipantes() != null) {
            grupo.getParticipantes().stream()
                    .map(Usuario::getNombre)
                    .filter(Objects::nonNull)
                    .forEach(lvGrupoParticipantes.getItems()::add);
        }
        if (grupo.getAdministrador() != null && grupo.getAdministrador().getNombre() != null) {
            lvGrupoParticipantes.getItems().add(grupo.getAdministrador().getNombre());
        }
        // Asegúrate de que el usuario actual esté en la lista
        if (usuario != null && !lvGrupoParticipantes.getItems().contains(usuario.getNombre())) {
            lvGrupoParticipantes.getItems().add(usuario.getNombre());
        }
    }

    private void actualizarListaGrupos(Grupo grupo) {
        if (!lvGruposDeUsuario.getItems().contains(grupo.getNombre())) {
            lvGruposDeUsuario.getItems().add(grupo.getNombre());
        }
        lvGruposDeUsuario.getSelectionModel().select(grupo.getNombre());
    }

    private void actualizarListaMensajes(Grupo grupo) {
        lvMensajesGrupo.getItems().clear();
        if (gestionMensajes.obtenerMensajes() != null) {
            gestionMensajes.obtenerMensajesPorGrupo(grupo).forEach(lvMensajesGrupo.getItems()::add);
        }
    }

    private void actualizarVistaGrupo() {
        tfGroup.setEditable(false);
        tfPasswordGroup.setEditable(false);
        btnRegisterSecretGroup.setDisable(true);
        btnRegisterPublicGroup.setDisable(true);
        btnIniciarGroup.setDisable(true);
        tfGroup.setText(grupoActual.getNombre());
        tfPasswordGroup.setText(grupoActual.getPassword());

        // Update participants list
        lvGrupoParticipantes.getItems().clear();
        lvGrupoParticipantes.getItems().addAll(grupoActual.getParticipantes().stream()
                .map(Usuario::getNombre)
                .toList());
        lvGrupoParticipantes.getItems().add(grupoActual.getAdministrador().getNombre());

        // Update messages list
        lvMensajesGrupo.getItems().clear();
        List<Mensaje> mensajesGrupo = gestionMensajes.obtenerMensajesDeGrupo(grupoActual);
        lvMensajesGrupo.getItems().addAll(mensajesGrupo);
    }


    private boolean sonDatosValidos() {
        return !tfAgregarUsuarioGrupo.getText().isEmpty() && grupoActual != null && !grupoActual.isPublico();
    }

    private boolean esAdministradorDelGrupo() {
        return grupoActual.getAdministrador().getNombre().equals(usuario.getNombre())
                && grupoActual.getAdministrador().getPassword().equals(usuario.getPassword());
    }

    private void agregarMiembroSiNoExiste(Usuario nuevoMiembro) {
        if (!grupoActual.getParticipantes().contains(nuevoMiembro)) {
            gestionGrupos.agregarMiembroGrupo(grupoActual, nuevoMiembro);
            gestionGrupos.actualizarGrupo(grupoActual);
            actualizarListaParticipantes();
            tfAgregarUsuarioGrupo.clear();
            mostrarAlerta(Alert.AlertType.INFORMATION, Constantes.TITULO_USUARIO_AGREGADO, Constantes.CONTENIDO_USUARIO_AGREGADO);
        } else {
            mostrarAlerta(Alert.AlertType.INFORMATION, Constantes.TITULO_USUARIO_YA_EXISTE, Constantes.CONTENIDO_USUARIO_YA_EXISTE);
        }
    }


    private void actualizarListaParticipantes() {
        lvGrupoParticipantes.getItems().clear();
        if (grupoActual.getParticipantes() != null) {
            List<String> participantes = grupoActual.getParticipantes().stream()
                    .filter(Objects::nonNull)
                    .map(Usuario::getNombre)
                    .toList();
            lvGrupoParticipantes.getItems().addAll(participantes);
        }
        if (grupoActual.getAdministrador() != null && grupoActual.getAdministrador().getNombre() != null) {
            lvGrupoParticipantes.getItems().add(grupoActual.getAdministrador().getNombre());
        }
        // Asegúrate de que el usuario actual esté en la lista si es miembro del grupo
        if (usuario != null && !lvGrupoParticipantes.getItems().contains(usuario.getNombre())) {
            lvGrupoParticipantes.getItems().add(usuario.getNombre());
        }
        lvGrupoParticipantes.refresh();
    }


    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String contenido) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}