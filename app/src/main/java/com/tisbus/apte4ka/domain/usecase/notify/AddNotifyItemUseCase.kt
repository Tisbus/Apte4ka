package com.tisbus.apte4ka.domain.usecase.notify

import com.tisbus.apte4ka.domain.entity.notify.Notify
import com.tisbus.apte4ka.domain.repostitory.notify.NotificationRepository
import javax.inject.Inject

class AddNotifyItemUseCase @Inject constructor(private val repository: NotificationRepository) {
    suspend fun addNotifyItem(item : Notify){
        repository.addNotifyItem(item)
    }
}