package com.tisbus.apte4ka.domain.usecase.notify

import com.tisbus.apte4ka.domain.repostitory.notify.NotificationRepository
import javax.inject.Inject

class DeleteNotifyItemUseCase @Inject constructor(private val repository: NotificationRepository) {
    suspend fun deleteNotifyItem(id : Int){
        repository.deleteNotifyItem(id)
    }
}