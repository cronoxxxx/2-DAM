package com.example.usersapp_adriansaavedra

import dagger.hilt.android.HiltAndroidApp

import android.app.Application

import timber.log.Timber
@HiltAndroidApp
class UserApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}