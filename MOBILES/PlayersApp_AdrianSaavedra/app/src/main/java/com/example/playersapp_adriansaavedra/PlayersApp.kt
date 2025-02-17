package com.example.playersapp_adriansaavedra

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp

class PlayersApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}

//pasar cuerpo de un solo parametro solo por nombre como en jugador
//puedo pasar un id de usuario en el authenticationresponse
//de cuantas pantallas quieres la app