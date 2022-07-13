package com.example.apte4ka.data.repository.aidkit

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.apte4ka.data.room.aidkit.AidKitDao
import com.example.apte4ka.data.room.aidkit.AidKitDatabase
import com.example.apte4ka.domain.entity.aidkit.AidKit
import com.example.apte4ka.domain.repostitory.aidkit.AidKitRepository

class AidKitRepositoryImpl(application : Application) : AidKitRepository {

    private val db = AidKitDatabase.getInstance(application).aidKitDao()

    override fun getAidKitList(): LiveData<MutableList<AidKit>> = db.getAidKitList()

    override fun getAidKitItem(id: Int): AidKit {
        return db.getAidKitItem(id)
    }

    override fun addAidKitItem(item: AidKit) {
        db.addAidKitItem(item)
    }

    override fun editAidKitItem(item: AidKit) {
        db.addAidKitItem(item)
    }

    override fun deleteAidKitItem(id: Int) {
        db.deleteAidKitItem(id)
    }

    override fun deleteAidKitAll() {
        db.deleteAidKitAll()
    }
}