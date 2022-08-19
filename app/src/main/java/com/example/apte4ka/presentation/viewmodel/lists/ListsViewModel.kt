package com.example.apte4ka.presentation.viewmodel.lists

import androidx.lifecycle.ViewModel
import com.example.apte4ka.domain.usecase.lists.GetSymptomListUseCase
import javax.inject.Inject

class ListsViewModel @Inject constructor(
    private val getSymptomListUseCase: GetSymptomListUseCase
) : ViewModel() {
    val listSymptom = getSymptomListUseCase.getSymptomList()
}