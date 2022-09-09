package com.tisbus.apte4ka.domain.usecase.notify

import androidx.lifecycle.LiveData
import com.tisbus.apte4ka.domain.entity.notify.Notify
import com.tisbus.apte4ka.domain.repostitory.notify.NotificationRepository
import javax.inject.Inject

class GetNotifyListUseCase @Inject constructor(private val repository: NotificationRepository) {
    fun getNotifyList() : LiveData<MutableList<Notify>>{
        return repository.getNotifyList()
    }
}