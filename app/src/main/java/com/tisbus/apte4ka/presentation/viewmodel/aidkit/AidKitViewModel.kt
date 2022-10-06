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
import kotlin.random.Random

class AidKitViewModel @Inject constructor(
    private val addAidKitItemUseCase: AddAidKitItemUseCase,
    private val getAidKitListUseCase: GetAidKitListUseCase,
    private val getAidKitItemUseCase: GetAidKitItemUseCase,
    private val editAidKitItemUseCase: EditAidKitItemUseCase,
    private val deleteAidKitAllUseCase: DeleteAidKitAllUseCase,
    private val deleteAidKitItemUseCase: DeleteAidKitItemUseCase,
) : ViewModel() {

    private val _aidCheckNameError = MutableLiveData<Boolean>()
    val aidCheckNameError : LiveData<Boolean>
    get() = _aidCheckNameError

    private val _aidCheckDescError = MutableLiveData<Boolean>()
    val aidCheckDescError : LiveData<Boolean>
        get() = _aidCheckDescError

    private fun checkTextError(name : String, text : String) : Boolean{
        var result = true
        if(name.isBlank()){
            _aidCheckNameError.value = true
                result = false
        }else{
            _aidCheckNameError.value = false
        }
        if(text.isBlank()){
            _aidCheckDescError.value = true
            result = false
        }else{
            _aidCheckDescError.value = false
        }
        return result
    }

    fun resetCheckNameError(){
        _aidCheckNameError.value = false
    }
    fun resetCheckDescError(){
        _aidCheckDescError.value = false
    }

    private var _aidKitLD = MutableLiveData<AidKit>()
    val aidKitLD: LiveData<AidKit>
        get() = _aidKitLD

    val listAidKit = getAidKitListUseCase.getAidKitList()

    fun addAidKit(
        name: String,
        description: String,
        icon: String,
    ) {
        if(checkTextError(name, description)){
            viewModelScope.launch {
                addAidKitItemUseCase.addAidKitItem(AidKit(name, description, icon))
            }
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

    fun editAidKitItem(name: String, description: String, icon: String) {
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