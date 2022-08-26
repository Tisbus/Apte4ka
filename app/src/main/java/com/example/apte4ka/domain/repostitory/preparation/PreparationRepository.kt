package com.example.apte4ka.domain.repostitory.preparation

import androidx.lifecycle.LiveData
import com.example.apte4ka.domain.entity.preparation.Preparation

interface PreparationRepository {
    fun getPreparationList() : LiveData<MutableList<Preparation>>
    fun getPreparationItem(id : Int) : Preparation
    fun addPreparationItem(item : Preparation)
    fun editPreparationItem(item : Preparation)
    fun copyPreparationItem(item : Preparation)
    fun deletePreparationItem(id : Int)
    fun deletePrepItemAidId(id : Int)
    fun deletePreparationAll()
    fun updateNotify()
}