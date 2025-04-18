package com.example.usersapp_adriansaavedra.data.local.di

import android.content.Context
import androidx.room.Room
import com.example.usersapp_adriansaavedra.data.local.AppDatabase
import com.example.usersapp_adriansaavedra.ui.Constantes
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
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideUserDao(appDatabase: AppDatabase) = appDatabase.userDao()

}


