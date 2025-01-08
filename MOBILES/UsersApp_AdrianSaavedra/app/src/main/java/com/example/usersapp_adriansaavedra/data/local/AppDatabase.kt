package com.example.usersapp_adriansaavedra.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.usersapp_adriansaavedra.data.local.modelo.UserEntity

@Database(entities = [UserEntity::class], version = 6, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}