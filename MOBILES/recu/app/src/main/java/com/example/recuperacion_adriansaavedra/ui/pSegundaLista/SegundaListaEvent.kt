package com.example.recuperacion_adriansaavedra.ui.pSegundaLista

sealed class SegundaListaEvent {
    data object AvisoVisto : SegundaListaEvent()
    data object CargarDatos : SegundaListaEvent()
    data class ItemSeleccionado(val item: String) : SegundaListaEvent() // Reemplazar con tu modelo
}