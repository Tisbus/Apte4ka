package com.tisbus.apte4ka.domain.repostitory.lists

import androidx.lifecycle.LiveData
import com.tisbus.apte4ka.domain.entity.packaging.Packaging
import com.tisbus.apte4ka.domain.entity.symptom.Symptom

interface ListsRepository {
    //Symptoms
    fun getListSymptoms(): LiveData<MutableList<Symptom>>
    suspend fun addSymptomItem(item : Symptom)
    suspend fun editSymptomItem(item : Symptom)
    suspend fun deleteSymptomItem(id : Int)
    suspend fun deleteAllSymptom()
    //Packaging
    fun getListPackaging(): LiveData<MutableList<Packaging>>
    suspend fun addPackagingItem(item : Packaging)
    suspend fun editPackagingItem(item : Packaging)
    suspend fun deletePackagingItem(id : Int)
    suspend fun deleteAllPackaging()
}