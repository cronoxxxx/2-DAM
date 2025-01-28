package com.example.hospitalapp_adriansaavedra.domain.modelo

data class MedRecord(
    val id: Int,
    val description : String,
    val date : String,
    val idPatient : Int,
    val medications: List<String>
)