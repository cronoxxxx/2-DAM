package com.example.hospitalapp_adriansaavedra.domain.modelo

data class Patient (
    val id: Int,
    val name: String,
    val birthDate: String,
    val phone: String,
    val paid: Double
)

