package com.tisbus.apte4ka.presentation.viewmodel.preparation

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.preference.PreferenceManager
import androidx.work.WorkManager
import com.tisbus.apte4ka.data.worker.WorkerUpdateNotify
import com.tisbus.apte4ka.domain.entity.preparation.Preparation
import com.tisbus.apte4ka.domain.entity.symptom.Symptom
import com.tisbus.apte4ka.domain.usecase.preparation.*
import javax.inject.Inject

class PreparationViewModel @Inject constructor(
    private val addPreparationItemUseCase: AddPreparationItemUseCase,
    private val deletePreparationItemUseCase: DeletePreparationItemUseCase,
    private val deletePreparationAllUseCase: DeletePreparationAllUseCase,
    private val editPreparationItemUseCase: EditPreparationItemUseCase,
    private val getPreparationItemUseCase: GetPreparationItemUseCase,
    private val getPreparationListUseCase: GetPreparationListUseCase,
    private val copyPreparationItemUseCase: CopyPreparationItemUseCase,
    private val updateNotificationUseCase: UpdateNotificationUseCase,
    private val deletePrepItemAidIdUseCase: DeletePrepItemAidIdUseCase,
    private val application: Application
) : ViewModel() {

    val listPreparation = getPreparationListUseCase.getPreparationList()

    private var _prepLD = MutableLiveData<Preparation>()
    val prepLD: LiveData<Preparation>
        get() = _prepLD



    init {
        val sharedP = PreferenceManager.getDefaultSharedPreferences(application)
        val isCheckStatus = sharedP.getBoolean("notify", true)
        if(isCheckStatus){
            updateNotificationUseCase()
        }else{
            WorkManager.getInstance(application).cancelUniqueWork(WorkerUpdateNotify.NAME_WORKER)
        }
    }

    fun deletePrepItemAidId(id  :Int){
        deletePrepItemAidIdUseCase.deletePrepItemAidId(id)
    }

    fun addPreparationItem(
        aidKitId: Int,
        name: String,
        image: String,
        symptoms: MutableList<Symptom>,
        packaging: String,
        description: String,
        dateCreate: String,
        dateExp: String,
    ) {
        val itemPreparation = Preparation(
            aidKitId,
            name,
            image,
            symptoms,
            packaging,
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
        packaging: String,
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
                packaging = packaging,
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
        packaging: String,
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
                packaging = packaging,
                description = description,
                dataCreate = dateCreate,
                dateExp = dateExp,
                id = Preparation.UNDEFINED_ID
            )
            copyPreparationItemUseCase.copyPreparationItem(item)
        }
    }
}