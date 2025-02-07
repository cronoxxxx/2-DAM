package com.example.consolesapp_adriansaavedra.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.consolesapp_adriansaavedra.data.local.model.*

@Database(entities = [ConsoleEntity::class, PlayerEntity::class, PlayerConsoleCrossRef::class], version = 3, exportSchema = true)
abstract class AppDatabase : RoomDatabase(){
    abstract val playerConsoleDao: PlayerConsoleDao
}