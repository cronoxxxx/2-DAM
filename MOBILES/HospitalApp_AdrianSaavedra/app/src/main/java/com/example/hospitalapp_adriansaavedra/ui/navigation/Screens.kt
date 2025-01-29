package com.example.hospitalapp_adriansaavedra.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import com.example.hospitalapp_adriansaavedra.R
import com.example.hospitalapp_adriansaavedra.ui.common.BottomNavItem

import kotlinx.serialization.Serializable

@Serializable
data object LoginDestination

@Serializable
data object MedRecordListDestination

@Serializable
data class MedRecordDetailDestination(val patientId: Int, val recordId: Int)

@Serializable
object PatientListDestination

@Serializable
data class PatientDetailDestination(val patientId: Int)


@Composable
fun rememberBottomNavItems(): List<BottomNavItem> {
    val recordsTitle = stringResource(R.string.records)
    val patientsTitle = stringResource(R.string.patients)

    return remember {
        listOf(
            BottomNavItem(
                title = recordsTitle,
                destination = MedRecordListDestination,
                icon = Icons.AutoMirrored.Filled.List
            ),
            BottomNavItem(
                title = patientsTitle,
                destination = PatientListDestination,
                icon = Icons.Default.Person
            )
        )
    }
}

