package com.tisbus.apte4ka.data.repository.notify

import androidx.lifecycle.LiveData
import com.tisbus.apte4ka.data.room.notify.NotifyDao
import com.tisbus.apte4ka.domain.entity.notify.Notify
import com.tisbus.apte4ka.domain.repostitory.notify.NotificationRepository
import javax.inject.Inject

class NotifyRepositoryImpl @Inject constructor(val db: NotifyDao) : NotificationRepository {
    override fun getNotifyList(): LiveData<MutableList<Notify>> = db.getNotifyList()

    override suspend fun getNotifyItem(id: Int): Notify {
        return db.getNotifyItem(id)
    }

    override suspend fun addNotifyItem(item: Notify) {
        db.addNotifyItem(item)
    }

    override suspend fun deleteNotifyItem(id: Int) {
        db.deleteNotifyItem(id)
    }
}