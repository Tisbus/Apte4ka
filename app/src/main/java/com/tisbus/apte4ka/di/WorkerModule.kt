package com.tisbus.apte4ka.di

import com.tisbus.apte4ka.data.worker.ChildWorkerFactory
import com.tisbus.apte4ka.data.worker.WorkerUpdateNotify
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface WorkerModule {
    @Binds
    @IntoMap
    @WorkerKey(WorkerUpdateNotify::class)
    fun bindWorkerUpdateNotify(factory: WorkerUpdateNotify.Factory): ChildWorkerFactory
}