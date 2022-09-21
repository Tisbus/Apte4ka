package com.tisbus.apte4ka.domain.repostitory.preparation

import androidx.lifecycle.LiveData
import com.tisbus.apte4ka.domain.entity.preparation.Preparation

interface PreparationRepository {
    fun getPreparationList() : LiveData<MutableList<Preparation>>
    suspend fun getPreparationItem(id : Int) : Preparation
    suspend fun addPreparationItem(item : Preparation)
    suspend fun editPreparationItem(item : Preparation)
    suspend fun copyPreparationItem(item : Preparation)
    suspend fun deletePreparationItem(id : Int)
    suspend fun deletePrepItemAidId(id : Int)
    suspend fun deletePreparationAll()
    fun updateNotify()
}