package com.example.buzonfxspring_adriansaavedra.ui;


import com.example.buzonfxspring_adriansaavedra.domain.errors.ErrorApp;
import com.example.buzonfxspring_adriansaavedra.domain.errors.ErrorAppDataBase;
import com.example.buzonfxspring_adriansaavedra.domain.errors.ErrorAppDatosNoValidos;
import com.example.buzonfxspring_adriansaavedra.domain.model.*;
import com.example.buzonfxspring_adriansaavedra.common.Constantes;
import com.example.buzonfxspring_adriansaavedra.domain.service.impl.GestionGrupos;
import com.example.buzonfxspring_adriansaavedra.domain.service.impl.GestionMensajes;
import com.example.buzonfxspring_adriansaavedra.domain.service.impl.GestionUsuarios;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class BuzonController implements Initializable {
    @FXML
    private TextField tfPasswordGroupFromList;
    @FXML
    private TextField tfNewSecretGroup;
    @FXML
    private ListView<String> lvGruposDeUsuario;
    @FXML
    private ListView<Mensaje> lvMensajesGrupo;
    @FXML
    private TextField tfNewPublicGroup;
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
    private TextField tfnewPasswordUser;
    private final GestionUsuarios gestionUsuarios;
    private final GestionGrupos gestionGrupos;
    private final GestionMensajes gestionMensajes;
    private Usuario usuario;
    private Grupo grupoActual;
    private String selectedGroupName;
    private boolean isSelectedGroupPublic;
    private String passwordGrupoActual;

    public BuzonController(GestionUsuarios gestionUsuarios, GestionGrupos gestionGrupos, GestionMensajes gestionMensajes) {

        this.gestionUsuarios = gestionUsuarios;
        this.gestionGrupos = gestionGrupos;
        this.gestionMensajes = gestionMensajes;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Arrays.asList(lvGruposSecretos, lvMensajesGrupo, lvGruposDeUsuario).forEach(lv -> lv.setItems(FXCollections.observableArrayList()));
        lvGrupoParticipantes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        deshabilitarCamposYBotones();
        setupGroupListeners();
    }
    @FXML
    private void registrarUser() {
        if (!tfNewUser.getText().isEmpty() && !tfnewPasswordUser.getText().isEmpty()) {
            Usuario registro = new Usuario(tfNewUser.getText(), "");
            gestionUsuarios.addUsuario(registro, tfnewPasswordUser.getText())
                    .peek(success -> mostrarAlerta(Alert.AlertType.INFORMATION, Constantes.TITULO_EXITO, Constantes.CONTENIDO_EXITO_REGISTRO))
                    .peekLeft(this::mostrarError);
        } else {
            mostrarAlerta(Alert.AlertType.WARNING, Constantes.TITULO_ADVERTENCIA, Constantes.CONTENIDO_ADVERTENCIA_CAMPOS_VACIOS);
        }
    }

    @FXML
    private void loginUser() {
        if (!tfUser.getText().isEmpty() && !tfPasswordUser.getText().isEmpty()) {
            Usuario x = new Usuario(tfUser.getText(), "");
            gestionUsuarios.login(x, tfPasswordUser.getText())
                    .peek(this::procesarLoginExitoso)
                    .peekLeft(this::mostrarError);
        } else {
            mostrarAlerta(Alert.AlertType.WARNING, Constantes.TITULO_ADVERTENCIA, Constantes.CONTENIDO_ADVERTENCIA_DATOS_INVALIDOS);
        }
    }
    @FXML
    private void accederGrupoPublico() {
        if (sonCamposValidos()) {
            if (passwordGrupoActual == null) {
                String password = tfPasswordGroup.getText();
                Grupo x = new Grupo(tfGroup.getText(), password, usuario, true);
                gestionGrupos.ingresar(x)
                        .peek(grupo -> {
                            passwordGrupoActual = password;
                            procesarIngresoExitoso(grupo);
                        })
                        .peekLeft(this::mostrarError);
            } else {
                Grupo x = new Grupo(tfGroup.getText(), passwordGrupoActual, usuario, true);
                gestionGrupos.ingresar(x)
                        .peek(this::procesarIngresoExitoso)
                        .peekLeft(this::mostrarError);
            }
        } else {
            mostrarAlerta(Alert.AlertType.WARNING, Constantes.TITULO_ADVERTENCIA, Constantes.CONTENIDO_ADVERTENCIA_DATOS_INVALIDOS);
        }
    }

    @FXML
    private void registrarPublicGroup() {
        if (!tfNewPublicGroup.getText().isEmpty() && !tfnewPasswordGroup.getText().isEmpty()) {
            Grupo grupo = new Grupo(tfNewPublicGroup.getText(), tfnewPasswordGroup.getText(), usuario, true);
            gestionGrupos.addGroup(grupo)
                    .peek(success -> lvGruposDeUsuario.getItems().add(grupo.getNombre()))
                    .peekLeft(this::mostrarError);
        }
    }

    @FXML
    private void registrarSecretGroup() {
        if (!tfNewSecretGroup.getText().isEmpty()) {
            Grupo grupo = new Grupo(tfNewSecretGroup.getText(), null, usuario, false);
            gestionGrupos.addGroup(grupo)
                    .peek(success -> lvGruposSecretos.getItems().add(grupo.getNombre()))
                    .peekLeft(this::mostrarError);
        }
    }

    @FXML
    private void enviarMensajeGrupos() {
        if (grupoActual != null && !taContenidoEnviar.getText().isEmpty()) {
            List<String> destinatariosSeleccionados = new ArrayList<>(lvGrupoParticipantes.getSelectionModel().getSelectedItems());
            if (destinatariosSeleccionados.isEmpty()) {
                destinatariosSeleccionados = grupoActual.getParticipantes().stream()
                        .map(Usuario::getNombre)
                        .toList();
            }
            gestionUsuarios.buscarUsuariosPorNombres(destinatariosSeleccionados)
                    .peek(destinatarios -> {
                        Mensaje mensaje = new Mensaje(taContenidoEnviar.getText(), LocalDateTime.now(), usuario, destinatarios, grupoActual.getNombre());
                        gestionMensajes.addMensajes(mensaje, grupoActual)
                                .peek(success -> {
                                    actualizarListaMensajes();
                                    taContenidoEnviar.clear();
                                    lvGrupoParticipantes.getSelectionModel().clearSelection();
                                })
                                .peekLeft(this::mostrarError);
                    })
                    .peekLeft(this::mostrarError);
        }
    }

    private void actualizarListaMensajes() {
        gestionMensajes.obtenerMensajesDeGrupo(grupoActual)
                .peek(mensajesGrupo -> {
                    lvMensajesGrupo.getItems().clear();
                    lvMensajesGrupo.getItems().addAll(mensajesGrupo);
                })
                .peekLeft(this::mostrarError);
    }

    @FXML
    private void agregarUsuarioAGrupo() {
        if (sonDatosValidos()) {
            if (esAdministradorDelGrupo()) {
                if (!grupoActual.isPublico()) {
                    gestionUsuarios.buscarUsuarioPorNombre(tfAgregarUsuarioGrupo.getText())
                            .peek(this::agregarMiembroSiNoExiste)
                            .peekLeft(this::mostrarError);
                } else {
                    mostrarAlerta(Alert.AlertType.WARNING, Constantes.TITULO_ADVERTENCIA, Constantes.CONTENIDO_ADVERTENCIA_GRUPO_PUBLICO);
                }
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, Constantes.TITULO_PERMISO_DENEGADO, Constantes.CONTENIDO_PERMISO_DENEGADO);
            }
        } else {
            mostrarAlerta(Alert.AlertType.WARNING, Constantes.TITULO_ADVERTENCIA, Constantes.CONTENIDO_ADVERTENCIA_DATOS_INVALIDOS);
        }
    }
    @FXML
    private void accederGrupoFromLista() {
        if (selectedGroupName != null && usuario != null) {
            Grupo grupo;
            if (passwordGrupoActual == null) {
                String password = tfPasswordGroupFromList.getText();
                grupo = new Grupo(selectedGroupName, password, usuario, isSelectedGroupPublic);
            } else {
                // Usa la contraseña almacenada si ya existe
                grupo = new Grupo(selectedGroupName, passwordGrupoActual, usuario, isSelectedGroupPublic);
            }

            gestionGrupos.ingresar(grupo)
                    .peek(grupoIngresado -> {
                        if (grupoIngresado.isPublico()) {
                            // Almacena la contraseña solo si es un grupo público
                            passwordGrupoActual = grupoIngresado.getPassword();
                        }
                        procesarIngresoExitoso(grupoIngresado);
                    })
                    .peekLeft(this::mostrarError);
        } else {
            mostrarAlerta(Alert.AlertType.WARNING, Constantes.TITULO_ERROR_SELECCION_INVALIDA, Constantes.CONTENIDO_ERROR_SELECCION_INVALIDA);
        }
    }


    private void verificarYCargarGrupoSecreto(String nombreGrupo) {
        gestionGrupos.obtenerGrupoPorNombre(nombreGrupo)
                .peek(grupo -> {
                    if (!grupo.isPublico()) {
                        passwordGrupoActual = null;
                        grupoActual = grupo;
                        actualizarVistaGrupo();
                    }
                });
    }


    private void setupGroupListeners() {
        lvGruposDeUsuario.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                seleccionarGrupo(newValue, true);
            }
        });

        lvGruposSecretos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                seleccionarGrupo(newValue, false);
                verificarYCargarGrupoSecreto(newValue);
            }
        });
    }

    private void seleccionarGrupo(String nombreGrupo, boolean esPublico) {
        tfPasswordGroupFromList.clear();
        selectedGroupName = nombreGrupo;
        isSelectedGroupPublic = esPublico;
        (esPublico ? lvGruposSecretos : lvGruposDeUsuario).getSelectionModel().clearSelection();
    }

    private void procesarLoginExitoso(Usuario usuarioVerificado) {
        usuario = usuarioVerificado;
        limpiarCamposPostLogin();
        cargarGruposUsuario();
        activarBotonesGrupo();
    }


    private void limpiarCamposPostLogin(){
        tfGroup.clear();
        tfNewPublicGroup.clear();
        tfNewSecretGroup.clear();
        tfPasswordGroup.clear();
        tfnewPasswordGroup.clear();
        tfPasswordGroupFromList.clear();
        taContenidoEnviar.clear();
        lvGrupoParticipantes.getItems().clear();
        lvMensajesGrupo.getItems().clear();
        lvGruposDeUsuario.getItems().clear();
        lvGruposSecretos.getItems().clear();
        mostrarAlerta( Alert.AlertType.INFORMATION, Constantes.TITULO_EXITO, Constantes.CONTENIDO_EXITO_LOGIN);
    }


    private void activarBotonesGrupo() {
        btnIniciarGroup.setDisable(false);
        btnRegisterPublicGroup.setDisable(false);
        btnRegisterSecretGroup.setDisable(false);
        tfGroup.setEditable(true);
        tfPasswordGroup.setEditable(true);
        tfnewPasswordGroup.setEditable(true);
    }


    private void cargarGruposUsuario() {
        lvGruposDeUsuario.getItems().clear();
        lvGruposSecretos.getItems().clear();
        gestionGrupos.obtenerGruposParaUsuario(usuario.getNombre(), true)
                .peek(grupos -> lvGruposDeUsuario.getItems().addAll(grupos));
        gestionGrupos.obtenerGruposParaUsuario(usuario.getNombre(), false)
                .peek(grupos -> lvGruposSecretos.getItems().addAll(grupos));
    }



    private boolean sonCamposValidos() {
        return tfGroup.getText() != null && !tfGroup.getText().isEmpty() &&
                tfPasswordGroup.getText() != null && !tfPasswordGroup.getText().isEmpty();
    }

    private void procesarIngresoExitoso(Grupo grupoIngresado) {
        grupoActual = grupoIngresado;
        gestionGrupos.agregarMiembroGrupo(grupoActual, usuario)
                .flatMap(agregado -> gestionGrupos.obtenerGrupoPorNombre(grupoActual.getNombre()))
                .peek(grupoActualizado -> {
                    grupoActual = grupoActualizado;
                    actualizarVistaGrupo();
                    actualizarListaParticipantes();
                    actualizarListaGrupos(usuario.getNombre());
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Acceso Exitoso", "Has ingresado al grupo correctamente.");
                })
                .peekLeft(error -> mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo agregar al usuario como miembro."));
    }

    private void actualizarListaGrupos(String nombreUsuario) {
        gestionGrupos.obtenerGruposParaUsuario(nombreUsuario, true)
                .peek(grupos -> {
                    lvGruposDeUsuario.getItems().clear();
                    lvGruposDeUsuario.getItems().addAll(grupos);
                })
                .peekLeft(error -> mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudieron cargar los grupos."));
    }

    private void deshabilitarCamposYBotones() {
        tfGroup.setEditable(false);
        tfPasswordGroup.setEditable(false);
        btnRegisterSecretGroup.setDisable(true);
        btnRegisterPublicGroup.setDisable(true);
        btnIniciarGroup.setDisable(true);
        tfPasswordGroup.setEditable(false);
        tfnewPasswordGroup.setEditable(false);
    }



    private void actualizarListaParticipantes() {
        if (grupoActual != null) {
            lvGrupoParticipantes.getItems().clear();
            if (grupoActual.getParticipantes() != null) {
                List<String> participantes = grupoActual.getParticipantes().stream()
                        .map(Usuario::getNombre)
                        .distinct()
                        .toList();
                lvGrupoParticipantes.getItems().addAll(participantes);
            }
            lvGrupoParticipantes.refresh();
        }
    }

    private void actualizarVistaGrupo() {
        lvGrupoParticipantes.getItems().clear();
        lvGrupoParticipantes.getItems().addAll(grupoActual.getParticipantes().stream()
                .map(Usuario::getNombre)
                .toList());

        lvMensajesGrupo.getItems().clear();
        gestionMensajes.obtenerMensajesDeGrupo(grupoActual)
                .peekLeft(this::mostrarError)
                .peek(mensajesGrupo -> lvMensajesGrupo.getItems().addAll(mensajesGrupo));
    }


    private boolean sonDatosValidos() {
        return !tfAgregarUsuarioGrupo.getText().isEmpty() && grupoActual != null && !grupoActual.isPublico();
    }

    private boolean esAdministradorDelGrupo() {
        return grupoActual.getAdministrador().getNombre().equals(usuario.getNombre());
    }

    private void agregarMiembroSiNoExiste(Usuario nuevoMiembro) {
        if (grupoActual.getParticipantes().stream().noneMatch(p -> p.getNombre().equals(nuevoMiembro.getNombre()))) {
            gestionGrupos.agregarMiembroGrupo(grupoActual, nuevoMiembro)
                    .flatMap(agregado -> gestionGrupos.obtenerGrupoPorNombre(grupoActual.getNombre()))
                    .peek(grupoActualizado -> {
                        grupoActual = grupoActualizado;
                        actualizarListaParticipantes();
                        tfAgregarUsuarioGrupo.clear();
                        mostrarAlerta(Alert.AlertType.INFORMATION, Constantes.TITULO_EXITO, Constantes.CONTENIDO_EXITO_AGREGAR_USUARIO_GRUPO);
                    })
                    .peekLeft(this::mostrarError);
        } else {
            mostrarAlerta(Alert.AlertType.INFORMATION, Constantes.TITULO_INFORMACION, Constantes.CONTENIDO_INFORMACION_USUARIO_YA_ES_MIEMBRO_GRUPO);
        }
    }


    private void mostrarError(ErrorApp errorText) {
        Alert alertError = new Alert(Alert.AlertType.ERROR);
        alertError.setTitle(Constantes.TITULO_ERROR);
        String errorMensaje = "";
        if (errorText instanceof ErrorAppDataBase e) {
            if (e == ErrorAppDataBase.TIMEOUT || e == ErrorAppDataBase.NO_CONNECTION) {
                errorMensaje = Constantes.CONTENIDO_ERROR_TIMEOUT;
            }
        } else if (errorText instanceof ErrorAppDatosNoValidos(String message)) {
            errorMensaje = message;
        }

        alertError.setContentText(errorMensaje);
        alertError.showAndWait();
    }


    private void mostrarAlerta(Alert.AlertType alertType, String title, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}