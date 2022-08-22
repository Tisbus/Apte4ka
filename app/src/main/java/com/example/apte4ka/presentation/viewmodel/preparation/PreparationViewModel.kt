package com.example.apte4ka.presentation.viewmodel.preparation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.apte4ka.domain.entity.preparation.Preparation
import com.example.apte4ka.domain.entity.symptom.Symptom
import com.example.apte4ka.domain.usecase.preparation.*
import javax.inject.Inject

class PreparationViewModel @Inject constructor(
    private val addPreparationItemUseCase: AddPreparationItemUseCase,
    private val deletePreparationItemUseCase: DeletePreparationItemUseCase,
    private val deletePreparationAllUseCase: DeletePreparationAllUseCase,
    private val editPreparationItemUseCase: EditPreparationItemUseCase,
    private val getPreparationItemUseCase: GetPreparationItemUseCase,
    private val getPreparationListUseCase: GetPreparationListUseCase,
    private val copyPreparationItemUseCase: CopyPreparationItemUseCase,
    private val updateNotificationUseCase: UpdateNotificationUseCase
) : ViewModel() {

    val listPreparation = getPreparationListUseCase.getPreparationList()

    private var _prepLD = MutableLiveData<Preparation>()
    val prepLD: LiveData<Preparation>
        get() = _prepLD

    init {
        updateNotificationUseCase()
    }

    fun addPreparationItem(
        aidKitId: Int,
        name: String,
        image: String,
        symptoms: MutableList<Symptom>,
        packing: String,
        description: String,
        dateCreate: String,
        dateExp: String,
    ) {
        val itemPreparation = Preparation(
            aidKitId,
            name,
            image,
            symptoms,
            packing,
            description,
            dateCreate,
            dateExp)
        addPreparationItemUseCase.addPreparationItem(itemPreparation)
    }

    fun getPreparationItem(id: Int) {
        val item = getPreparationItemUseCase.getPreparationItem(id)
        _prepLD.value = item
    }

    fun deletePreparationAll() {
        deletePreparationAllUseCase.deletePreparationAll()
    }

    fun deletePreparationItem(id: Int) {
        deletePreparationItemUseCase.deletePreparationItem(id)
    }

    fun editPreparationItem(
        aidKitId: Int,
        name: String,
        image: String,
        symptoms: MutableList<Symptom>,
        packing: String,
        description: String,
        dateCreate: String,
        dateExp: String,
    ) {
        _prepLD.value?.let {
            val item = it.copy(
                aidKit = aidKitId,
                name = name,
                image = image,
                symptoms = symptoms,
                packing = packing,
                description = description,
                dataCreate = dateCreate,
                dateExp = dateExp
            )
            editPreparationItemUseCase.editPreparationItem(item)
        }
    }

    fun copyPreparationItem(
        aidKitId: Int,
        name: String,
        image: String,
        symptoms: MutableList<Symptom>,
        packing: String,
        description: String,
        dateCreate: String,
        dateExp: String,
    ) {
        _prepLD.value?.let {
            val item = it.copy(
                aidKit = aidKitId,
                name = name,
                image = image,
                symptoms = symptoms,
                packing = packing,
                description = description,
                dataCreate = dateCreate,
                dateExp = dateExp,
                id = Preparation.UNDEFINED_ID
            )
            copyPreparationItemUseCase.copyPreparationItem(item)
        }
    }
}