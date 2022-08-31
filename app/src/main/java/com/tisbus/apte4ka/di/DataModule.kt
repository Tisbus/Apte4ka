package com.tisbus.apte4ka.di

import android.app.Application
import com.tisbus.apte4ka.data.repository.aidkit.AidKitRepositoryImpl
import com.tisbus.apte4ka.data.repository.lists.ListsRepositoryImpl
import com.tisbus.apte4ka.data.repository.preparation.PreparationRepositoryImpl
import com.tisbus.apte4ka.data.room.aidkit.AidKitDao
import com.tisbus.apte4ka.data.room.aidkit.AidKitDatabase
import com.tisbus.apte4ka.data.room.preparation.PreparationDao
import com.tisbus.apte4ka.data.room.preparation.PreparationDatabase
import com.tisbus.apte4ka.domain.repostitory.aidkit.AidKitRepository
import com.tisbus.apte4ka.domain.repostitory.lists.ListsRepository
import com.tisbus.apte4ka.domain.repostitory.preparation.PreparationRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {
    @ApplicationScope
    @Binds
    fun bindAidKitRepository(impl: AidKitRepositoryImpl): AidKitRepository

    @ApplicationScope
    @Binds
    fun bindListRepository(impl: ListsRepositoryImpl): ListsRepository

    @ApplicationScope
    @Binds
    fun bindPreparationRepository(impl: PreparationRepositoryImpl): PreparationRepository

    companion object {
        @Provides
        @ApplicationScope
        fun provideAidKitDao(application: Application): AidKitDao {
            return AidKitDatabase.getInstance(application).aidKitDao()
        }

        @Provides
        @ApplicationScope
        fun providePreparationDao(application: Application): PreparationDao {
            return PreparationDatabase.getInstance(application).preparationDao()
        }
    }
}