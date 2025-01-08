package com.example.buzonfxspring_adriansaavedra.ui;
import com.example.buzonfxspring_adriansaavedra.domain.errors.*;
import com.example.buzonfxspring_adriansaavedra.domain.model.*;
import com.example.buzonfxspring_adriansaavedra.common.Constantes;
import com.example.buzonfxspring_adriansaavedra.domain.service.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.*;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import org.springframework.stereotype.Component;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class BuzonController implements Initializable {
    @FXML private Pane rootPane;
    @FXML private TextField tfPasswordGroupFromList;
    @FXML private TextField tfNewSecretGroup;
    @FXML private ListView<String> lvGruposDeUsuario;
    @FXML private TextField tfNewPublicGroup;
    @FXML private TextField tfnewPasswordGroup;
    @FXML private Button btnRegisterPublicGroup;
    @FXML private Button btnRegisterSecretGroup;
    @FXML private TextField tfGroup;
    @FXML private Button btnIniciarGroup;
    @FXML private TextField tfPasswordGroup;
    @FXML private ListView<String> lvGrupoParticipantes;
    @FXML private TextArea taContenidoEnviar;
    @FXML private ListView<String> lvGruposSecretos;
    @FXML private TextField tfAgregarUsuarioGrupo;
    @FXML private TextField tfNewUser;
    @FXML private TextField tfPasswordUser;
    @FXML private TextField tfUser;
    @FXML private TextField tfnewPasswordUser;
    private final GestionUsuarios gestionUsuarios;
    private final GestionGrupos gestionGrupos;
    private final GestionMensajes gestionMensajes;
    private Usuario usuario;
    private GrupoPublico grupoPublicoActual;
    private GrupoPrivado grupoPrivadoActual;
    private boolean isCurrentGroupPublic;
    private String selectedGroupName;
    private boolean isSelectedGroupPublic;
    private String userPass;
    @FXML private ListView<MensajePublico> lvMensajesGrupoPublico;
    @FXML private ListView<MensajePrivado> lvMensajesGrupoPrivado;

    public BuzonController(GestionUsuarios gestionUsuarios, GestionGrupos gestionGrupos, GestionMensajes gestionMensajes) {

        this.gestionUsuarios = gestionUsuarios;
        this.gestionGrupos = gestionGrupos;
        this.gestionMensajes = gestionMensajes;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Arrays.asList(lvGruposSecretos, lvMensajesGrupoPrivado, lvMensajesGrupoPublico, lvGruposDeUsuario).forEach(lv -> lv.setItems(FXCollections.observableArrayList()));
        lvGrupoParticipantes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        deshabilitarCamposYBotones();
        setupGroupListeners();
    }

    @FXML
    private void registrarUser() {
        if (tfNewUser.getText().isEmpty() || tfnewPasswordUser.getText().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, Constantes.TITULO_ADVERTENCIA, Constantes.CONTENIDO_ADVERTENCIA_CAMPOS_VACIOS);
            return;
        }
        Usuario registro = new Usuario(tfNewUser.getText());
        rootPane.setCursor(Cursor.WAIT);
        gestionUsuarios.register(registro, tfnewPasswordUser.getText())
                .thenAcceptAsync(result -> {
                    rootPane.setCursor(Cursor.DEFAULT);
                    result.peek(success -> mostrarAlerta(Alert.AlertType.INFORMATION, Constantes.TITULO_EXITO, Constantes.CONTENIDO_EXITO_REGISTRO)).peekLeft(this::mostrarError);
                }, Platform::runLater);
    }

    @FXML
    private void loginUser() {
        if (tfUser.getText().isEmpty() || tfPasswordUser.getText().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, Constantes.TITULO_ADVERTENCIA, Constantes.CONTENIDO_ADVERTENCIA_DATOS_INVALIDOS);
            return;
        }

        Usuario x = new Usuario(tfUser.getText());
        userPass = tfPasswordUser.getText();
        rootPane.setCursor(Cursor.WAIT);
        gestionUsuarios.login(x, userPass)
                .thenAcceptAsync(result -> {
                    rootPane.setCursor(Cursor.DEFAULT);
                    result.peek(usuarioVerificado -> procesarLoginExitoso(usuarioVerificado, userPass))
                            .peekLeft(this::mostrarError);
                }, Platform::runLater);
    }

    @FXML
    private void accederGrupoPublico() {
        if (!sonCamposValidos()) {
            mostrarAlerta(Alert.AlertType.WARNING, Constantes.TITULO_ADVERTENCIA, Constantes.CONTENIDO_ADVERTENCIA_DATOS_INVALIDOS);
            return;
        }

        String nombreGrupo = tfGroup.getText();
        String password = tfPasswordGroup.getText();
        rootPane.setCursor(Cursor.WAIT);

        gestionGrupos.loginGrupoPublico(nombreGrupo, password, usuario)
                .thenAcceptAsync(result -> {
                    rootPane.setCursor(Cursor.DEFAULT);
                    result.peek(grupo -> {
                        grupoPublicoActual = grupo;
                        isCurrentGroupPublic = true;
                        grupoPublicoActual.getParticipantes().add(usuario);
                        actualizarListaGruposUsuario(nombreGrupo);
                        cargarMensajesDelGrupoPublico();
                    }).peekLeft(this::mostrarError);
                }, Platform::runLater);
    }

    private void actualizarListaGruposUsuario(String nombreGrupo) {
        if (!lvGruposDeUsuario.getItems().contains(nombreGrupo)) {
            lvGruposDeUsuario.getItems().add(nombreGrupo);
        }
        lvGruposDeUsuario.refresh();
    }

    private void cargarMensajesDelGrupoPublico() {
        rootPane.setCursor(Cursor.WAIT);
        gestionMensajes.obtenerYDesencriptarMensajesPublicos(grupoPublicoActual)
                .thenAcceptAsync(result -> {
                    rootPane.setCursor(Cursor.DEFAULT);
                    result.peek(mensajes -> {
                        lvMensajesGrupoPublico.getItems().clear();
                        lvMensajesGrupoPublico.getItems().addAll(mensajes);
                        actualizarListaParticipantes();
                    }).peekLeft(this::mostrarError);
                }, Platform::runLater);
    }

    @FXML
    private void registrarPublicGroup() {
        if (tfNewPublicGroup.getText().isEmpty() || tfnewPasswordGroup.getText().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, Constantes.TITULO_ADVERTENCIA, Constantes.CONTENIDO_ADVERTENCIA_CAMPOS_VACIOS);
            return;
        }

        GrupoPublico grupoPublico = new GrupoPublico(tfNewPublicGroup.getText(), tfnewPasswordGroup.getText(), usuario);
        rootPane.setCursor(Cursor.WAIT);
        gestionGrupos.registrarGrupoPublico(grupoPublico)
                .thenAcceptAsync(result -> {
                    rootPane.setCursor(Cursor.DEFAULT);
                    result.peek(
                            success -> lvGruposDeUsuario.getItems().add(grupoPublico.getNombre())
                    ).peekLeft(this::mostrarError);
                }, Platform::runLater);
    }

    @FXML
    private void registrarSecretGroup() {
        if (tfNewSecretGroup.getText().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, Constantes.TITULO_ADVERTENCIA, Constantes.CONTENIDO_ADVERTENCIA_CAMPOS_VACIOS);
            return;
        }

        GrupoPrivado grupoPrivado = new GrupoPrivado(tfNewSecretGroup.getText(), usuario);
        rootPane.setCursor(Cursor.WAIT);
        gestionGrupos.registrarGrupoPrivado(grupoPrivado)
                .thenAcceptAsync(result -> {
                    rootPane.setCursor(Cursor.DEFAULT);
                    result.peek(success -> lvGruposSecretos.getItems().add(grupoPrivado.getNombre())).peekLeft(this::mostrarError);
                }, Platform::runLater);
    }

    @FXML
    private void enviarMensajeGrupos() {
        if (isCurrentGroupPublic) {
            enviarMensajePublico();
        } else {
            List<String> destinatariosSeleccionados = new ArrayList<>(lvGrupoParticipantes.getSelectionModel().getSelectedItems());
            if (destinatariosSeleccionados.isEmpty()) {
                mostrarAlerta(Alert.AlertType.WARNING, Constantes.SELECCION_DE_DESTINATARIOS, Constantes.POR_FAVOR_DESTINATARIO);
                return;
            }
            evaluarMensajePrivado(destinatariosSeleccionados);
        }
    }


    private void enviarMensajePublico() {
        if (grupoPublicoActual == null || taContenidoEnviar.getText().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, Constantes.ENVIO_DE_MENSAJE, Constantes.POR_FAVOR_ASEGURESE_DE_QUE_HAY_UN_GRUPO_SELECCIONADO_Y_EL_MENSAJE_NO_ESTA_VACIO);
            return;
        }

        MensajePublico mensajePublico = new MensajePublico(
                taContenidoEnviar.getText(),
                LocalDateTime.now(),
                usuario,
                grupoPublicoActual.getParticipantes(),
                grupoPublicoActual.getNombre()
        );

        rootPane.setCursor(Cursor.WAIT);
        gestionMensajes.addMensajePublico(mensajePublico, grupoPublicoActual, userPass)
                .thenAcceptAsync(result -> result.peek(success -> {
                    actualizarListaMensajesPublicos();
                    taContenidoEnviar.clear();
                }).peekLeft(this::mostrarError), Platform::runLater);
    }


    private void evaluarMensajePrivado(List<String> destinatariosSeleccionados) {
        if (grupoPrivadoActual == null || taContenidoEnviar.getText().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, Constantes.ALERTA_ENVIO_MENSAJE_TITULO, Constantes.ALERTA_ENVIO_MENSAJE_CUERPO);
            return;
        }

        List<String> mutableDestinatarios = new ArrayList<>(destinatariosSeleccionados);
        if (!mutableDestinatarios.contains(usuario.getNombre())) {
            mutableDestinatarios.add(usuario.getNombre());
        }

        List<Usuario> destinatariosUsuarios = mutableDestinatarios.stream()
                .map(Usuario::new)
                .toList();

        MensajePrivado mensajePrivado = hacerMensajePrivado(destinatariosUsuarios);
        enviarMensajePrivado(mensajePrivado);
    }

    private MensajePrivado hacerMensajePrivado(List<Usuario> destinatarios) {
        return new MensajePrivado(taContenidoEnviar.getText(),
                LocalDateTime.now(),
                usuario,
                destinatarios,
                grupoPrivadoActual.getNombre());
    }

    private void enviarMensajePrivado(MensajePrivado mensajePrivado) {
        rootPane.setCursor(Cursor.WAIT);
        gestionMensajes.addMensajePrivado(mensajePrivado, userPass)
                .thenAcceptAsync(result -> result.peek(success -> {
                    actualizarListaMensajesPrivados();
                    limpiarCamposEnvioMensaje();
                }).peekLeft(this::mostrarError), Platform::runLater);
    }

    private void limpiarCamposEnvioMensaje() {
        taContenidoEnviar.clear();
        lvGrupoParticipantes.getSelectionModel().clearSelection();
    }


    private void actualizarListaMensajesPublicos() {

        gestionMensajes.obtenerYDesencriptarMensajesPublicos(grupoPublicoActual)
                .thenAcceptAsync(result ->{
                    rootPane.setCursor(Cursor.DEFAULT);
                    result.peek(
                        mensajesGrupo -> {
                            lvMensajesGrupoPublico.getItems().clear();
                            lvMensajesGrupoPublico.getItems().addAll(mensajesGrupo);
                        }
                ).peekLeft(this::mostrarError);}, Platform::runLater);
    }


    private void actualizarListaMensajesPrivados() {

        gestionMensajes.obtenerYDesencriptarMensajesPrivados(grupoPrivadoActual, usuario, userPass)
                .thenAcceptAsync(result -> {
                    rootPane.setCursor(Cursor.DEFAULT);
                        result.peek(
                        mensajesDesencriptados -> {
                            lvMensajesGrupoPrivado.getItems().clear();
                            lvMensajesGrupoPrivado.getItems().addAll(mensajesDesencriptados);
                        }
                ).peekLeft(this::mostrarError);}, Platform::runLater);
    }

    @FXML
    private void agregarUsuarioAGrupo() {
        if (!sonDatosValidos()) {
            mostrarAlerta(Alert.AlertType.WARNING, Constantes.TITULO_ADVERTENCIA, Constantes.CONTENIDO_ADVERTENCIA_DATOS_INVALIDOS);
            return;
        }

        if (!esAdministradorDelGrupo()) {
            mostrarAlerta(Alert.AlertType.ERROR, Constantes.TITULO_PERMISO_DENEGADO, Constantes.CONTENIDO_PERMISO_DENEGADO);
            return;
        }

        rootPane.setCursor(Cursor.WAIT);
        gestionUsuarios.buscarUsuarioPorNombre(tfAgregarUsuarioGrupo.getText())
                .thenAcceptAsync(result -> {
                    rootPane.setCursor(Cursor.DEFAULT);
                    result.peek(this::agregarMiembroSiNoExiste).peekLeft(this::mostrarError);
                }, Platform::runLater);
    }

    @FXML
    private void accederGrupoDesdeLista() {
        if (selectedGroupName == null || usuario == null) {
            mostrarAlerta(Alert.AlertType.WARNING, Constantes.TITULO_ERROR_SELECCION_INVALIDA, Constantes.CONTENIDO_ERROR_SELECCION_INVALIDA);
            return;
        }

        if (!isSelectedGroupPublic) {
            mostrarAlerta(Alert.AlertType.WARNING, Constantes.TITULO_ERROR_SELECCION_INVALIDA, Constantes.NO_ES_GRUPO_PUBLICO);
            return;
        }

        String password = tfPasswordGroupFromList.getText();
        rootPane.setCursor(Cursor.WAIT);
        gestionGrupos.loginGrupoPublico(selectedGroupName, password, usuario)
                .thenAcceptAsync(result -> {
                    rootPane.setCursor(Cursor.DEFAULT);
                    result.peek(
                            grupo -> {
                                grupoPublicoActual = grupo;
                                isCurrentGroupPublic = true;
                                grupoPublicoActual.getParticipantes().add(usuario);
                                cargarMensajesDelGrupoPublico();
                            }
                    ).peekLeft(this::mostrarError);
                }, Platform::runLater);
    }


    private void verificarYCargarGrupoSecreto(String nombreGrupo) {
        rootPane.setCursor(Cursor.WAIT);
        gestionGrupos.obtenerGrupoPrivadoPorNombre(nombreGrupo)
                .thenAcceptAsync(result -> {
                    rootPane.setCursor(Cursor.DEFAULT);
                    result.peek(grupo -> {
                        grupoPrivadoActual = grupo;
                        isCurrentGroupPublic = false;
                        cargarMensajesDelGrupoPrivado();
                    }).peekLeft(this::mostrarError);
                }, Platform::runLater);
    }


    private void cargarMensajesDelGrupoPrivado() {
        rootPane.setCursor(Cursor.WAIT);
        gestionMensajes.obtenerYDesencriptarMensajesPrivados(grupoPrivadoActual, usuario, userPass)
                .thenAcceptAsync(result -> {
                    rootPane.setCursor(Cursor.DEFAULT);
                    result.peek(mensajesDesencriptados -> {
                        lvMensajesGrupoPrivado.getItems().clear();
                        lvMensajesGrupoPrivado.getItems().addAll(mensajesDesencriptados);
                        actualizarListaParticipantes();
                    }).peekLeft(this::mostrarError);
                }, Platform::runLater);
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
        (esPublico ? lvMensajesGrupoPrivado : lvMensajesGrupoPublico).getItems().clear();
    }

    private void procesarLoginExitoso(Usuario usuarioVerificado, String password) {
        usuario = usuarioVerificado;
        userPass = password;
        limpiarCamposPostLogin();
        cargarGruposUsuario();
        activarBotonesGrupo();
    }


    private void limpiarCamposPostLogin() {
        tfGroup.clear();
        tfNewPublicGroup.clear();
        tfNewSecretGroup.clear();
        tfPasswordGroup.clear();
        tfnewPasswordGroup.clear();
        tfPasswordGroupFromList.clear();
        taContenidoEnviar.clear();
        lvGrupoParticipantes.getItems().clear();
        lvMensajesGrupoPrivado.getItems().clear();
        lvMensajesGrupoPublico.getItems().clear();
        lvGruposDeUsuario.getItems().clear();
        lvGruposSecretos.getItems().clear();
        mostrarAlerta(Alert.AlertType.INFORMATION, Constantes.TITULO_EXITO, Constantes.CONTENIDO_EXITO_LOGIN);
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
        cargarGruposPublicos();
        cargarGruposPrivados();
    }

    private void cargarGruposPublicos() {
        gestionGrupos.obtenerGruposParaUsuario(usuario, true)
                .thenAcceptAsync(result -> result.peek(grupos -> lvGruposDeUsuario.getItems().addAll(grupos))
                .peekLeft(this::mostrarError), Platform::runLater);
    }

    private void cargarGruposPrivados() {
        gestionGrupos.obtenerGruposParaUsuario(usuario, false)
                .thenAcceptAsync(result -> {
                    rootPane.setCursor(Cursor.DEFAULT);
                    result.peek(grupos -> lvGruposSecretos.getItems().addAll(grupos))
                            .peekLeft(this::mostrarError);
                }, Platform::runLater);
    }

    private boolean sonCamposValidos() {
        return tfGroup.getText() != null && !tfGroup.getText().isEmpty() &&
                tfPasswordGroup.getText() != null && !tfPasswordGroup.getText().isEmpty();
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
        lvGrupoParticipantes.getItems().clear();
        List<String> participantes;
        if (isCurrentGroupPublic && grupoPublicoActual != null) {
            participantes = grupoPublicoActual.getParticipantes().stream()
                    .map(Usuario::getNombre)
                    .distinct()
                    .toList();
        } else if (!isCurrentGroupPublic && grupoPrivadoActual != null) {
            participantes = grupoPrivadoActual.getParticipantes().stream()
                    .map(Usuario::getNombre)
                    .distinct()
                    .toList();
        } else {
            participantes = new ArrayList<>();
        }
        lvGrupoParticipantes.getItems().addAll(participantes);
        lvGrupoParticipantes.refresh();
    }



    private boolean sonDatosValidos() {
        return !tfAgregarUsuarioGrupo.getText().isEmpty() &&
                (grupoPublicoActual != null || grupoPrivadoActual != null);
    }

    private boolean esAdministradorDelGrupo() {
        if (isCurrentGroupPublic) {
            return grupoPublicoActual.getAdministrador().getNombre().equals(usuario.getNombre());
        } else {
            return grupoPrivadoActual.getAdministrador().getNombre().equals(usuario.getNombre());
        }
    }

    private void agregarMiembroSiNoExiste(Usuario nuevoMiembro) {
        if (isCurrentGroupPublic) {
            mostrarAlerta(Alert.AlertType.WARNING, Constantes.TITULO_PERMISO_DENEGADO, Constantes.NO_ES_GRUPO_PRIVADO);
            return;
        }

        rootPane.setCursor(Cursor.WAIT);
        gestionGrupos.agregarMiembroGrupoPrivado(grupoPrivadoActual, nuevoMiembro)
                .thenAcceptAsync(result -> {
                    rootPane.setCursor(Cursor.DEFAULT);
                    result.peek(success -> {
                        grupoPrivadoActual.getParticipantes().add(nuevoMiembro);
                        actualizarListaParticipantes();
                        tfAgregarUsuarioGrupo.clear();
                        mostrarAlerta(Alert.AlertType.INFORMATION, Constantes.TITULO_EXITO, Constantes.CONTENIDO_EXITO_AGREGAR_USUARIO_GRUPO);
                    }).peekLeft(this::mostrarError);
                }, Platform::runLater);
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