package org.example.simulacroweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SimulacroWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimulacroWebApplication.class, args);
    }

}

/***
 *
 * Crear una aplicación web con Spring Boot y Thymeleaf que gestione una lista de productos con los siguientes atributos:
 *
 * id (autogenerado)
 * nombre
 * precio
 * cantidad en stock
 * Crear una página principal donde se listen todos los productos en una tabla.
 *
 * Crear un formulario para añadir productos desde la web (sin stock inicial).
 *
 * Crear una página de detalles donde se pueda ver la información de un producto seleccionado.
 *
 * Crear una funcionalidad para eliminar productos desde la tabla de la página principal.
 *
 *
 *
 * */