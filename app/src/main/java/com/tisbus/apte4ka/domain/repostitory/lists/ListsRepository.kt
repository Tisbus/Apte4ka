package com.tisbus.apte4ka.domain.repostitory.lists

import androidx.lifecycle.LiveData
import com.tisbus.apte4ka.domain.entity.packaging.Packaging
import com.tisbus.apte4ka.domain.entity.symptom.Symptom

interface ListsRepository {
    //Symptoms
    fun getListSymptoms(): LiveData<MutableList<Symptom>>
    suspend fun getSymptomItem(id : Int) : Symptom
    suspend fun addSymptomItem(id : Int)
    suspend fun editSymptomItem(id : Int)
    suspend fun deleteSymptomItem(id : Int)
    suspend fun deleteAllSymptom()
    //Packaging
    fun getListPackaging(): LiveData<MutableList<Packaging>>
    suspend fun getPackagingItem(id : Int) : Packaging
    suspend fun addPackagingItem(id : Int)
    suspend fun editPackagingItem(id : Int)
    suspend fun deletePackagingItem(id : Int)
    suspend fun deleteAllPackaging()
}