package com.example.apte4ka.presentation.viewmodel.preparation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.apte4ka.data.repository.preparation.PreparationRepositoryImpl
import com.example.apte4ka.domain.entity.preparation.Preparation
import com.example.apte4ka.domain.entity.symptom.Symptom
import com.example.apte4ka.domain.usecase.preparation.*

class PreparationViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = PreparationRepositoryImpl(application)
    private val addPreparationItemUseCase = AddPreparationItemUseCase(repository)
    private val deletePreparationItemUseCase = DeletePreparationItemUseCase(repository)
    private val deletePreparationAllUseCase = DeletePreparationAllUseCase(repository)
    private val editPreparationItemUseCase = EditPreparationItemUseCase(repository)
    private val getPreparationItemUseCase = GetPreparationItemUseCase(repository)
    private val getPreparationListUseCase = GetPreparationListUseCase(repository)
    private val copyPreparationItemUseCase = CopyPreparationItemUseCase(repository)

    val listPreparation = getPreparationListUseCase.getPreparationList()

    private var _prepLD = MutableLiveData<Preparation>()
    val prepLD: LiveData<Preparation>
        get() = _prepLD

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
        _prepLD.value = getPreparationItemUseCase.getPreparationItem(id)
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
        _prepLD.value?.let{
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
        _prepLD.value?.let{
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