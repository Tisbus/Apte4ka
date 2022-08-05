package com.example.apte4ka.presentation.viewmodel.lists

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.apte4ka.data.repository.lists.ListsRepositoryImpl
import com.example.apte4ka.domain.usecase.lists.GetSymptomListUseCase

class ListsViewModel(application: Application)  :AndroidViewModel(application) {
    private val repository = ListsRepositoryImpl()
    private val getSymptomListUseCase = GetSymptomListUseCase(repository)

    val listSymptom = getSymptomListUseCase.getSymptomList()
}