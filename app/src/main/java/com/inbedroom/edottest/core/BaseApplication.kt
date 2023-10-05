package com.inbedroom.edottest.core

import android.app.Application

class BaseApplication: Application() {
    private lateinit var _repositoryModule: AppModule
    val movieRepository get() = _repositoryModule.getMovieRepository()

    override fun onCreate() {
        super.onCreate()
        _repositoryModule = AppModule(NetworkClient.createClient())
    }
}