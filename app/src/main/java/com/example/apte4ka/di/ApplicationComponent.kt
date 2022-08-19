package com.example.apte4ka.di

import android.app.Application
import com.example.apte4ka.presentation.fragment.*
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {
    fun inject(frag : AidKitAddFragment)
    fun inject(frag : AidKitEditFragment)
    fun inject(frag : AidKitDetailFragment)
    fun inject(frag : ListAidKitFragment)
    fun inject(frag : FilterFragment)
    fun inject(frag : SearchFragment)
    fun inject(frag : PreparationDetailFragment)
    fun inject(frag : PreparationCopyFragment)
    fun inject(frag : PreparationAddFragment)
    fun inject(frag : PreparationEditFragment)

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance application: Application) : ApplicationComponent
    }
}