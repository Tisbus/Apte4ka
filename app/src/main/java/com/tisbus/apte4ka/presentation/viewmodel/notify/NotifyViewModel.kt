package com.tisbus.apte4ka.presentation.viewmodel.notify

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tisbus.apte4ka.domain.entity.notify.Notify
import com.tisbus.apte4ka.domain.usecase.notify.AddNotifyItemUseCase
import com.tisbus.apte4ka.domain.usecase.notify.DeleteNotifyItemUseCase
import com.tisbus.apte4ka.domain.usecase.notify.GetNotifyItemUseCase
import com.tisbus.apte4ka.domain.usecase.notify.GetNotifyListUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class NotifyViewModel @Inject constructor(
    private val getNotifyListUseCase: GetNotifyListUseCase,
    private val getNotifyItemUseCase: GetNotifyItemUseCase,
    private val addNotifyItemUseCase: AddNotifyItemUseCase,
    private val deleteNotifyItemUseCase: DeleteNotifyItemUseCase
) : ViewModel() {

    val notifyList = getNotifyListUseCase.getNotifyList()

    private val _notifyLD = MutableLiveData<Notify>()
    val notifyLD : LiveData<Notify>
    get() = _notifyLD

    fun getNotifyItem(id : Int){
        viewModelScope.launch {
            val item = getNotifyItemUseCase.getNotifyItem(id)
            _notifyLD.value = item
        }
    }

    fun addNotifyItem(
        name : String,
        description : String,
        icon : String,
        status : Boolean
    ){
        viewModelScope.launch {
            addNotifyItemUseCase.addNotifyItem(Notify(
                name,
                description,
                icon,
                status
            ))
        }
    }

    fun deleteNotifyItem(id : Int){
        viewModelScope.launch {
            deleteNotifyItemUseCase.deleteNotifyItem(id)
        }
    }


}