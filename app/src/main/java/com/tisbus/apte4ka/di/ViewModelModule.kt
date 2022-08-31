package com.tisbus.apte4ka.di

import androidx.lifecycle.ViewModel
import com.tisbus.apte4ka.presentation.viewmodel.aidkit.AidKitViewModel
import com.tisbus.apte4ka.presentation.viewmodel.lists.ListsViewModel
import com.tisbus.apte4ka.presentation.viewmodel.preparation.PreparationViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @Binds
    @IntoMap
    @ModuleKey(AidKitViewModel::class)
    fun bindAidKitViewModel(model : AidKitViewModel) : ViewModel
    @Binds
    @IntoMap
    @ModuleKey(PreparationViewModel::class)
    fun bindPreparationViewModel(model : PreparationViewModel) : ViewModel
    @Binds
    @IntoMap
    @ModuleKey(ListsViewModel::class)
    fun bindListsViewModel(model : ListsViewModel) : ViewModel
}