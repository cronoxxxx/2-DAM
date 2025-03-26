package com.example.finalexamenmoviles_adriansaavedra.data

import com.example.finalexamenmoviles_adriansaavedra.data.local.InformeDao
import com.example.finalexamenmoviles_adriansaavedra.data.local.model.InformeEntity
import com.example.finalexamenmoviles_adriansaavedra.data.local.model.toEntity
import com.example.finalexamenmoviles_adriansaavedra.data.local.model.toInforme
import com.example.finalexamenmoviles_adriansaavedra.data.remote.NetworkResult
import com.example.finalexamenmoviles_adriansaavedra.domain.model.Informe
import com.example.finalexamenmoviles_adriansaavedra.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class InformeRepository @Inject constructor(
    private val informeDao: InformeDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    suspend fun getAllInformes(): NetworkResult<List<Informe>> = withContext(dispatcher) {
        try {
            val informes = informeDao.getAllInformes().map { it.toInforme() }
            if (informes.isEmpty()) {
                NetworkResult.Error("No se encontraron informes")
            } else {
                NetworkResult.Success(informes)
            }
        } catch (e: Exception) {
            NetworkResult.Error("Error al obtener informes: ${e.message}")
        }
    }
    
    suspend fun getInforme(id: Int): NetworkResult<Informe> = withContext(dispatcher) {
        try {
            val informe = informeDao.getInforme(id).toInforme()
            NetworkResult.Success(informe)
        } catch (e: Exception) {
            NetworkResult.Error("Error al obtener informe: ${e.message}")
        }
    }
    
    suspend fun updateInforme(informe: Informe): NetworkResult<Unit> = withContext(dispatcher) {
        try {
            informeDao.updateInforme(informe.toEntity())
            NetworkResult.Success(Unit)
        } catch (e: Exception) {
            NetworkResult.Error("Error al actualizar informe: ${e.message}")
        }
    }
    
    suspend fun insertInitialInformes(): NetworkResult<Unit> = withContext(dispatcher) {
        try {
            val informes = listOf(
                InformeEntity(nombre = "Informe Trimestral", nivel = 1),
                InformeEntity(nombre = "Informe Semestral", nivel = 2),
                InformeEntity(nombre = "Informe Anual", nivel = 3)
            )
            
            informes.forEach { informeDao.insertInforme(it) }
            NetworkResult.Success(Unit)
        } catch (e: Exception) {
            NetworkResult.Error("Error al insertar informes iniciales: ${e.message}")
        }
    }
}