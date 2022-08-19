package com.example.apte4ka.data.repository.aidkit

import androidx.lifecycle.LiveData
import com.example.apte4ka.data.room.aidkit.AidKitDao
import com.example.apte4ka.domain.entity.aidkit.AidKit
import com.example.apte4ka.domain.repostitory.aidkit.AidKitRepository
import javax.inject.Inject

class AidKitRepositoryImpl @Inject constructor(private val db : AidKitDao) : AidKitRepository {

    override fun getAidKitList(): LiveData<MutableList<AidKit>> = db.getAidKitList()

    override suspend fun getAidKitItem(id: Int): AidKit {
        return db.getAidKitItem(id)
    }

    override suspend fun addAidKitItem(item: AidKit) {
        db.addAidKitItem(item)
    }

    override suspend fun editAidKitItem(item: AidKit) {
        db.addAidKitItem(item)
    }

    override suspend fun deleteAidKitItem(id: Int) {
        db.deleteAidKitItem(id)
    }

    override suspend fun deleteAidKitAll() {
        db.deleteAidKitAll()
    }
}