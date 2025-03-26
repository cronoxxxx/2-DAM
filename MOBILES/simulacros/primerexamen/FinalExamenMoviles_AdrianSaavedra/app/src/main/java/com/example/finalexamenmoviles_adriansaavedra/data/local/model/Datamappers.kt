package com.example.finalexamenmoviles_adriansaavedra.data.local.model

import com.example.finalexamenmoviles_adriansaavedra.domain.model.Informe

fun InformeEntity.toInforme(): Informe {
    return Informe(
        id = this.id,
        nombre = this.nombre,
        nivel = this.nivel
    )
}

fun Informe.toEntity(): InformeEntity {
    return InformeEntity(
        id = this.id,
        nombre = this.nombre,
        nivel = this.nivel
    )
}

