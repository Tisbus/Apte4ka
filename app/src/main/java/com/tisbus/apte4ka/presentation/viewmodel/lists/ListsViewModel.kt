package com.tisbus.apte4ka.presentation.viewmodel.lists

import androidx.lifecycle.ViewModel
import com.tisbus.apte4ka.domain.usecase.lists.GetPackagingListUseCase
import com.tisbus.apte4ka.domain.usecase.lists.GetSymptomListUseCase
import javax.inject.Inject

class ListsViewModel @Inject constructor(
    private val getSymptomListUseCase: GetSymptomListUseCase,
    private val getPackagingListUseCase: GetPackagingListUseCase,
) : ViewModel() {
    val listSymptom = getSymptomListUseCase.getSymptomList()
    val listPackaging = getPackagingListUseCase.getPackagingList()
}