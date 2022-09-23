package com.tisbus.apte4ka.presentation.viewmodel.aidkit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tisbus.apte4ka.domain.entity.aidkit.AidKit
import com.tisbus.apte4ka.domain.usecase.aidkit.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class AidKitViewModel @Inject constructor(
    private val addAidKitItemUseCase: AddAidKitItemUseCase,
    private val getAidKitListUseCase: GetAidKitListUseCase,
    private val getAidKitItemUseCase: GetAidKitItemUseCase,
    private val editAidKitItemUseCase: EditAidKitItemUseCase,
    private val deleteAidKitAllUseCase: DeleteAidKitAllUseCase,
    private val deleteAidKitItemUseCase: DeleteAidKitItemUseCase
) : ViewModel() {


    private var _aidKitLD = MutableLiveData<AidKit>()
    val aidKitLD: LiveData<AidKit>
        get() = _aidKitLD

    val listAidKit = getAidKitListUseCase.getAidKitList()

    fun addAidKit(
        name: String,
        description: String,
        icon : Int,
    ) {
        viewModelScope.launch {
            addAidKitItemUseCase.addAidKitItem(AidKit(name, description, icon))
        }
    }

    fun deleteAidKitItem(id: Int) {
        viewModelScope.launch {
            deleteAidKitItemUseCase.deleteAidKitItem(id)
        }
    }

    fun getAidKitItem(id: Int) {
        viewModelScope.launch {
            val aidItem = getAidKitItemUseCase.getAidKitItem(id)
            _aidKitLD.value = aidItem
        }
    }

    fun deleteAidKitAll() {
        viewModelScope.launch {
            deleteAidKitAllUseCase.deleteAidKitAll()
        }
    }

    fun editAidKitItem(name: String, description: String, icon: Int) {
        _aidKitLD.value?.let {
            viewModelScope.launch {
                val item = it.copy(
                    name = name,
                    description = description,
                    icon = icon
                )
                editAidKitItemUseCase.editAidKitItem(item)
            }
        }
    }

}