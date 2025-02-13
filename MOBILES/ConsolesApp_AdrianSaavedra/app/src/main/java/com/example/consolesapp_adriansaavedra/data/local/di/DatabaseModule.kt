package com.example.consolesapp_adriansaavedra.data.local.di

import android.content.Context
import androidx.room.Room
import com.example.consolesapp_adriansaavedra.data.local.AppDatabase
import com.example.consolesapp_adriansaavedra.ui.Constantes
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            Constantes.APP_DB
        ).createFromAsset(Constantes.DB_ASSETS).fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideUserDao(appDatabase: AppDatabase) = appDatabase.playerConsoleDao

}