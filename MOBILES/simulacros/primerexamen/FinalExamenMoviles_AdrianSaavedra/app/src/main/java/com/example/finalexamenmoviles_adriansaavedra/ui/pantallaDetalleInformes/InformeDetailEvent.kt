package com.example.finalexamenmoviles_adriansaavedra.ui.pantallaDetalleInformes

sealed class InformeDetailEvent {
    data object AvisoVisto : InformeDetailEvent()
    data class GetInforme(val id: Int) : InformeDetailEvent()
    data class UpdateNivel(val nivel: Int) : InformeDetailEvent()
}
