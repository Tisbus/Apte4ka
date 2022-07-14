package com.example.apte4ka.presentation.viewmodel.aidkit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.apte4ka.data.repository.aidkit.AidKitRepositoryImpl
import com.example.apte4ka.domain.entity.aidkit.AidKit
import com.example.apte4ka.domain.usecase.aidkit.*

class AidKitViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = AidKitRepositoryImpl(application)
    private val addAidKitItemUseCase = AddAidKitItemUseCase(repository)
    private val getAidKitListUseCase = GetAidKitListUseCase(repository)
    private val getAidKitItemUseCase = GetAidKitItemUseCase(repository)
    private val editAidKitItemUseCase = EditAidKitItemUseCase(repository)
    private val deleteAidKitAllUseCase = DeleteAidKitAllUseCase(repository)
    private val deleteAidKitItemUseCase = DeleteAidKitItemUseCase(repository)

    private val _aidKitLD = MutableLiveData<AidKit>()
    val aidKitLD: LiveData<AidKit>
        get() = _aidKitLD

    val listAidKit = getAidKitListUseCase.getAidKitList()

    fun addAidKit(
        name: String,
        description: String,
    ) {
        addAidKitItemUseCase.addAidKitItem(AidKit(name, description))
    }

    fun deleteAidKitItem(id: Int) {
        deleteAidKitItemUseCase.deleteAidKitItem(id)
    }

    fun getAidKitItem(id: Int) {
        val aidItem = getAidKitItemUseCase.getAidKitItem(id)
        _aidKitLD.value = aidItem
    }

    fun deleteAidKitAll() {
        deleteAidKitAllUseCase.deleteAidKitAll()
    }

    fun editAidKitItem(name: String, description: String) {
        _aidKitLD.value?.let {
            val item = it.copy(
                name = name,
                description = description
            )
            editAidKitItemUseCase.editAidKitItem(item)
        }
    }

}