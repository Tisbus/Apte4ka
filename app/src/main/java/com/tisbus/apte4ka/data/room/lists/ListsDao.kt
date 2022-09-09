package com.tisbus.apte4ka.data.room.lists

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tisbus.apte4ka.domain.entity.packaging.Packaging
import com.tisbus.apte4ka.domain.entity.symptom.Symptom

@Dao
interface ListsDao {

    //Symptom
    @Query("SELECT * FROM symptom")
    fun getListSymptoms(): LiveData<MutableList<Symptom>>

    @Query("SELECT * FROM symptom WHERE id = :id")
    suspend fun getSymptomItem(id: Int): Symptom

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSymptomItem(item: Symptom)

    @Query("DELETE FROM symptom WHERE id = :id")
    suspend fun deleteSymptomItem(id: Int)

    @Query("DELETE FROM symptom")
    suspend fun deleteAllSymptom()

    //Packaging
    @Query("SELECT * FROM packaging")
    fun getListPackaging(): LiveData<MutableList<Packaging>>

    @Query("SELECT * FROM packaging WHERE id = :id")
    suspend fun getPackagingItem(id: Int): Packaging

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPackagingItem(item: Packaging)

    @Query("DELETE FROM packaging WHERE id = :id")
    suspend fun deletePackagingItem(id: Int)

    @Query("DELETE FROM packaging")
    suspend fun deleteAllPackaging()

}