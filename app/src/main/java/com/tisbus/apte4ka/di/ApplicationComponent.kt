package com.tisbus.apte4ka.di

import android.app.Application
import com.tisbus.apte4ka.presentation.AidKitApp
import com.tisbus.apte4ka.presentation.fragment.aidkit.AidKitAddFragment
import com.tisbus.apte4ka.presentation.fragment.aidkit.AidKitDetailFragment
import com.tisbus.apte4ka.presentation.fragment.aidkit.AidKitEditFragment
import com.tisbus.apte4ka.presentation.fragment.filter.FilterFragment
import com.tisbus.apte4ka.presentation.fragment.main.ListAidKitFragment
import com.tisbus.apte4ka.presentation.fragment.preparation.PreparationAddFragment
import com.tisbus.apte4ka.presentation.fragment.preparation.PreparationCopyFragment
import com.tisbus.apte4ka.presentation.fragment.preparation.PreparationDetailFragment
import com.tisbus.apte4ka.presentation.fragment.preparation.PreparationEditFragment
import com.tisbus.apte4ka.presentation.fragment.search.SearchFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class, WorkerModule::class])
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
    fun inject(app : AidKitApp)

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance application: Application) : ApplicationComponent
    }
}