package com.example.buzonfxspring_adriansaavedra.domain.model;

import lombok.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
@Data
public class MensajePrivado {
    private List<MensajePrivadoUserClave> mensajesPrivadosUserClave;
    private String texto;
    private LocalDateTime fecha;
    private Usuario owner;
    private List<Usuario> destinatarios;
    private String group;
    private String firma;


    public MensajePrivado(String texto, LocalDateTime fecha, Usuario owner, List<Usuario> destinatarios, String group) {
        mensajesPrivadosUserClave = new ArrayList<>();
        this.texto = texto;
        this.fecha = fecha;
        this.owner = owner;
        this.destinatarios = destinatarios;
        this.group = group;
    }
    @Override
    public String toString() {
        return String.format("Texto='%s', Fecha=%s, Enviador=%s, Destinatarios=%s", texto, fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), owner.getNombre(), destinatarios.toString());
    }
}