package com.tisbus.apte4ka.data.repository.lists

import androidx.lifecycle.LiveData
import com.tisbus.apte4ka.data.lists.packaging.ListPackaging
import com.tisbus.apte4ka.data.lists.symptom.ListSymptom
import com.tisbus.apte4ka.data.room.lists.ListsDao
import com.tisbus.apte4ka.domain.entity.packaging.Packaging
import com.tisbus.apte4ka.domain.entity.symptom.Symptom
import com.tisbus.apte4ka.domain.repostitory.lists.ListsRepository
import javax.inject.Inject

class ListsRepositoryImpl @Inject constructor(val db : ListsDao) : ListsRepository {

    //Symptom

    override fun getListSymptoms(): LiveData<MutableList<Symptom>> {
        return db.getListSymptoms()
    }

    override suspend fun getSymptomItem(id: Int): Symptom {
        TODO("Not yet implemented")
    }

    override suspend fun addSymptomItem(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun editSymptomItem(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSymptomItem(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllSymptom() {
        TODO("Not yet implemented")
    }

    //Packaging

    override fun getListPackaging(): LiveData<MutableList<Packaging>> {
        return db.getListPackaging()
    }

    override suspend fun getPackagingItem(id: Int): Packaging {
        TODO("Not yet implemented")
    }

    override suspend fun addPackagingItem(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun editPackagingItem(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun deletePackagingItem(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllPackaging() {
        TODO("Not yet implemented")
    }

}