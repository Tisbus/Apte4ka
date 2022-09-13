package com.tisbus.apte4ka.data.repository.lists

import androidx.lifecycle.LiveData
import com.tisbus.apte4ka.data.room.lists.ListsDao
import com.tisbus.apte4ka.domain.entity.packaging.Packaging
import com.tisbus.apte4ka.domain.entity.symptom.Symptom
import com.tisbus.apte4ka.domain.repostitory.lists.ListsRepository
import javax.inject.Inject

class ListsRepositoryImpl @Inject constructor(val db: ListsDao) : ListsRepository {

    //Symptom

    override fun getListSymptoms(): LiveData<MutableList<Symptom>> = db.getListSymptoms()

    override suspend fun addSymptomItem(item: Symptom) {
        db.addSymptomItem(item)
    }

    override suspend fun editSymptomItem(item: Symptom) {
        db.addSymptomItem(item)
    }

    override suspend fun deleteSymptomItem(id: Int) {
        db.deleteSymptomItem(id)
    }

    override suspend fun deleteAllSymptom() {
        db.deleteAllSymptom()
    }

    //Packaging

    override fun getListPackaging(): LiveData<MutableList<Packaging>> = db.getListPackaging()

    override suspend fun addPackagingItem(item: Packaging) {
        db.addPackagingItem(item)
    }

    override suspend fun editPackagingItem(item: Packaging) {
        db.addPackagingItem(item)
    }

    override suspend fun deletePackagingItem(id: Int) {
        db.deletePackagingItem(id)
    }

    override suspend fun deleteAllPackaging() {
        db.deleteAllPackaging()
    }

}