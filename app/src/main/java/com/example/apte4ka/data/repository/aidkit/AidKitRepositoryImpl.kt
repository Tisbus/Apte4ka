package com.example.apte4ka.data.repository.aidkit

import androidx.lifecycle.LiveData
import com.example.apte4ka.data.room.aidkit.AidKitDao
import com.example.apte4ka.domain.entity.aidkit.AidKit
import com.example.apte4ka.domain.repostitory.aidkit.AidKitRepository

class AidKitRepositoryImpl(private val db : AidKitDao) : AidKitRepository {

    override fun getAidKitList(): LiveData<MutableList<AidKit>> = db.getAidKitList()

    override fun getAidKitItem(id: Int): AidKit {
        return db.getAidKitItem(id)
    }

    override fun addAidKitItem(item: AidKit) {
        db.addAidKitItem(item)
    }

    override fun editAidKitItem(id: Int) {
        db.editAidKitItem(id)
    }

    override fun deleteAidKitItem(id: Int) {
        db.deleteAidKitItem(id)
    }

    override fun deleteAidKitAll() {
        db.deleteAidKitAll()
    }
}