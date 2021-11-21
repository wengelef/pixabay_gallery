package com.wengelef.pixabaygallery

import android.app.Application

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        RepositoryComponent.initDriver(this)
    }
}

