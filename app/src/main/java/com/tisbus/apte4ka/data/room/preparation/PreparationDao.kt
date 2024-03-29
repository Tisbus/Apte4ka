package com.tisbus.apte4ka.data.room.preparation

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tisbus.apte4ka.domain.entity.preparation.Preparation

@Dao
interface PreparationDao {
    @Query("SELECT * FROM preparation")
    fun getPreparationList(): LiveData<MutableList<Preparation>>

    @Query("SELECT * FROM preparation")
    suspend fun getPreparationL(): MutableList<Preparation>

    @Query("SELECT * FROM preparation WHERE id = :id LIMIT 1")
    suspend fun getPreparationItem(id: Int): Preparation

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPreparationItem(item: Preparation)

    @Query("DELETE FROM preparation WHERE id = :id")
    suspend fun deletePreparationItem(id: Int)

    @Query("DELETE FROM preparation")
    suspend fun deletePreparationAll()

    @Query("DELETE FROM preparation WHERE aidKit =:id")
    suspend fun deletePrepItemAidId(id : Int)
}