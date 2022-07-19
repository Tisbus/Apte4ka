package com.example.apte4ka.presentation.viewmodel.preparation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.apte4ka.data.repository.preparation.PreparationRepositoryImpl
import com.example.apte4ka.domain.usecase.preparation.*

class PreparationViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = PreparationRepositoryImpl(application)
    private val addPreparationItemUseCase = AddPreparationItemUseCase(repository)
    private val deletePreparationItemUseCase = DeletePreparationItemUseCase(repository)
    private val deletePreparationAllUseCase = DeletePreparationAllUseCase(repository)
    private val editPreparationItemUseCase = EditPreparationItemUseCase(repository)
    private val getPreparationItemUseCase = GetPreparationItemUseCase(repository)
    private val getPreparationListUseCase = GetPreparationListUseCase(repository)

    val listPreparation = getPreparationListUseCase.getPreparationList()
}