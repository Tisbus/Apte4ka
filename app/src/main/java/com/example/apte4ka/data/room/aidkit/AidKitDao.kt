package com.example.apte4ka.data.room.aidkit

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.apte4ka.domain.entity.aidkit.AidKit

@Dao
interface AidKitDao {
    @Query("SELECT * FROM aid_kit")
    fun getAidKitList(): LiveData<MutableList<AidKit>>
    @Query("SELECT * FROM aid_kit WHERE id = :id LIMIT 1")
    fun getAidKitItem(id: Int): AidKit
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAidKitItem(item: AidKit)
    @Query("DELETE FROM aid_kit WHERE id = :id")
    fun deleteAidKitItem(id: Int)
    @Query("DELETE FROM aid_kit")
    fun deleteAidKitAll()
}