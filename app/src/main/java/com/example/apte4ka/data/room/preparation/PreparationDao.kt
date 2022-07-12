package com.example.apte4ka.data.room.preparation

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.apte4ka.domain.entity.preparation.Preparation

@Dao
interface PreparationDao {
    @Query("SELECT * FROM preparation")
    fun getPreparationList(): LiveData<MutableList<Preparation>>

    @Query("SELECT * FROM preparation WHERE id = :id LIMIT 1")
    fun getPreparationItem(id: Int): Preparation

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPreparationItem(item: Preparation)

    @Query("SELECT * FROM preparation WHERE id = :id")
    fun editPreparationItem(id: Int)

    @Query("DELETE FROM preparation WHERE id = :id")
    fun deletePreparationItem(id: Int)

    @Query("DELETE FROM preparation")
    fun deletePreparationAll()
}