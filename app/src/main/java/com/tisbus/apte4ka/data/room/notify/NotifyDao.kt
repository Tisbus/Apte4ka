package com.tisbus.apte4ka.data.room.notify

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tisbus.apte4ka.domain.entity.notify.Notify

@Dao
interface NotifyDao {
    @Query("SELECT * FROM notify")
    fun getNotifyList(): LiveData<MutableList<Notify>>

    @Query("SELECT * FROM notify")
    fun getMLNotifyList(): MutableList<Notify>

    @Query("SELECT * FROM notify WHERE id = :id")
    suspend fun getNotifyItem(id: Int): Notify

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNotifyItem(item: Notify)

    @Query("DELETE FROM notify WHERE id = :id")
    suspend fun deleteNotifyItem(id: Int)
}