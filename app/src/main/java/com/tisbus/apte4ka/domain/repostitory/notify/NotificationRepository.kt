package com.tisbus.apte4ka.domain.repostitory.notify

import androidx.lifecycle.LiveData
import com.tisbus.apte4ka.domain.entity.notify.Notify

interface NotificationRepository {
    fun getNotifyList() : LiveData<MutableList<Notify>>
    suspend fun getNotifyItem(id : Int) : Notify
    suspend fun addNotifyItem(item : Notify)
    suspend fun deleteNotifyItem(id  :Int)
}