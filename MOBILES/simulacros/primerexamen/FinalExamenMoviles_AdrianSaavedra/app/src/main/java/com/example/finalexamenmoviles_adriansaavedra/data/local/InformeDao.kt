package com.example.finalexamenmoviles_adriansaavedra.data.local

import androidx.room.*
import com.example.finalexamenmoviles_adriansaavedra.data.local.model.InformeEntity

@Dao
interface InformeDao {
    @Query("SELECT * FROM informes")
    suspend fun getAllInformes(): List<InformeEntity>

    @Query("SELECT * FROM informes WHERE id = :informeId")
    suspend fun getInforme(informeId: Int): InformeEntity

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertInforme(informe: InformeEntity): Long

    @Update
    suspend fun updateInforme(informe: InformeEntity)
}
