package com.example.apte4ka.di

import androidx.work.WorkerFactory
import com.example.apte4ka.data.worker.ChildWorkerFactory
import com.example.apte4ka.data.worker.WorkerNotifyFactory
import com.example.apte4ka.data.worker.WorkerUpdateNotify
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface WorkerModule {
    @Binds
    @IntoMap
    @WorkerKey(WorkerUpdateNotify::class)
    fun bindWorkerUpdateNotify(factory: WorkerUpdateNotify.Factory): ChildWorkerFactory

    @Binds
    fun bindWorkerFactory(factory: WorkerNotifyFactory): WorkerFactory
}