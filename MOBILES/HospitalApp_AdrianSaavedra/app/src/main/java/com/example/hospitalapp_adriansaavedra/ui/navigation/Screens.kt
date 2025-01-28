package com.example.hospitalapp_adriansaavedra.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Person
import com.example.hospitalapp_adriansaavedra.ui.common.BottomNavItem
import kotlinx.serialization.Serializable

@Serializable
object LoginDestination
@Serializable
data object MedRecordDestination
@Serializable
data class MedRecordDetailDestination(val patientId: String, val recordId: String)
@Serializable
object PatientListDestination
@Serializable
data class PatientDetailDestination(val patientId: String)

val bottomNavItems = listOf(
    BottomNavItem("Records", MedRecordDestination, Icons.AutoMirrored.Filled.List),
    BottomNavItem("Patients", PatientListDestination, Icons.Default.Person)
)