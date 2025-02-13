package com.example.hospitalapp_adriansaavedra.domain.modelo

data class MedRecord(
    val id: Int = 0,
    val description: String = "",
    val date: String = "",
    val idPatient: Int = 0,
    val medications: List<String> = emptyList()
)