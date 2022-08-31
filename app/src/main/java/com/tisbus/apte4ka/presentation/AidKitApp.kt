package com.tisbus.apte4ka.presentation

import android.app.Application
import androidx.work.Configuration
import com.tisbus.apte4ka.data.worker.WorkerNotifyFactory
import com.tisbus.apte4ka.di.DaggerApplicationComponent
import javax.inject.Inject

class AidKitApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: WorkerNotifyFactory

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

    override fun onCreate() {
        component.inject(this)
        super.onCreate()
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    }
}