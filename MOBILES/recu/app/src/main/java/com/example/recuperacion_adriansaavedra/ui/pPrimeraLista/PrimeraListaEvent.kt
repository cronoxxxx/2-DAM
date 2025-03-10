package com.example.recuperacion_adriansaavedra.ui.pPrimeraLista

sealed class PrimeraListaEvent {
    data object AvisoVisto : PrimeraListaEvent()
    data object CargarDatos : PrimeraListaEvent()
    data class ItemSeleccionado(val item: String) : PrimeraListaEvent()
}