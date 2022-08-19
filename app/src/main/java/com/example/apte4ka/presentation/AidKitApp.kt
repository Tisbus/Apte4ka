package com.example.apte4ka.presentation

import android.app.Application
import com.example.apte4ka.di.DaggerApplicationComponent

class AidKitApp : Application() {
    val component by lazy{
        DaggerApplicationComponent.factory().create(this)
    }
}