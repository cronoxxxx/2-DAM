package com.example.finalexamenmoviles_adriansaavedra.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.finalexamenmoviles_adriansaavedra.data.local.model.InformeEntity

@Database(
    entities = [InformeEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val informeDao: InformeDao
}
