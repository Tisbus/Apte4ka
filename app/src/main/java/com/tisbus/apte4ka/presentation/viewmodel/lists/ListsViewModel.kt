package com.tisbus.apte4ka.presentation.viewmodel.lists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tisbus.apte4ka.domain.entity.packaging.Packaging
import com.tisbus.apte4ka.domain.entity.symptom.Symptom
import com.tisbus.apte4ka.domain.usecase.lists.packaging.*
import com.tisbus.apte4ka.domain.usecase.lists.symptom.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListsViewModel @Inject constructor(
    private val getSymptomListUseCase: GetSymptomListUseCase,
    private val addSymptomItemUseCase: AddSymptomItemUseCase,
    private val editSymptomItemUseCase: EditSymptomItemUseCase,
    private val deleteSymptomItemUseCase: DeleteSymptomItemUseCase,
    private val deleteAllSymptomUseCase: DeleteAllSymptomUseCase,
    private val getPackagingListUseCase: GetPackagingListUseCase,
    private val addPackagingItemUseCase: AddPackagingItemUseCase,
    private val editPackagingItemUseCase: EditPackagingItemUseCase,
    private val deletePackagingItemUseCase: DeletePackagingItemUseCase,
    private val deleteAllPackagingUseCase: DeleteAllPackagingUseCase,
) : ViewModel() {
    //Symptom
    val listSymptom = getSymptomListUseCase.getSymptomList()

    fun addSymptomItem(
        name: String,
        icon: String,
        status: Boolean,
    ) {
        viewModelScope.launch {
            addSymptomItemUseCase.addSymptomItem(Symptom(
                name,
                icon,
                status
            ))
        }
    }

    fun editSymptomItem(
        name: String,
        icon: String,
        status: Boolean,
        symptom: Symptom,
    ) {
        viewModelScope.launch {
            val item = symptom.copy(
                name = name,
                icon = icon,
                status = status
            )
            editSymptomItemUseCase.editSymptomItem(item)
        }
    }

    fun deleteSymptomItem(id: Int) {
        viewModelScope.launch {
            deleteSymptomItemUseCase.deleteSymptomItem(id)
        }
    }

    fun deleteSymptomAll() {
        viewModelScope.launch {
            deleteAllSymptomUseCase.deleteAllSymptom()
        }
    }

    //Packaging
    val listPackaging = getPackagingListUseCase.getPackagingList()

    fun addPackagingItem(
        name: String,
        icon: String,
        status: Boolean,
    ) {
        viewModelScope.launch {
            addPackagingItemUseCase.addPackagingItem(
                Packaging(
                    name,
                    icon,
                    status
                )
            )
        }
    }

    fun editPackagingItem(
        name: String,
        icon: String,
        status: Boolean,
        packaging: Packaging,
    ) {
        viewModelScope.launch {
            val item = packaging.copy(
                name = name,
                icon = icon,
                status = status
            )
            editPackagingItemUseCase.editPackagingItem(item)
        }
    }

    fun deletePackagingItem(id: Int) {
        viewModelScope.launch {
            deletePackagingItemUseCase.deletePackagingItem(id)
        }
    }

    fun deletePackagingAll() {
        viewModelScope.launch {
            deleteAllPackagingUseCase.deleteAllPackaging()
        }
    }
}