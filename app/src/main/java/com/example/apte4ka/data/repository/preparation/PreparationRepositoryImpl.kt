package com.example.apte4ka.data.repository.preparation

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.apte4ka.data.room.preparation.PreparationDao
import com.example.apte4ka.data.room.preparation.PreparationDatabase
import com.example.apte4ka.domain.entity.aidkit.AidKit
import com.example.apte4ka.domain.entity.preparation.Preparation
import com.example.apte4ka.domain.repostitory.preparation.PreparationRepository

class PreparationRepositoryImpl(application: Application) : PreparationRepository {

    private val db = PreparationDatabase.getInstance(application).preparationDao()

    override fun getPreparationList(): LiveData<MutableList<Preparation>> = db.getPreparationList()

    override fun getPreparationItem(id: Int): Preparation {
        return db.getPreparationItem(id)
    }

    override fun addPreparationItem(item: Preparation) {
        db.addPreparationItem(item)
    }

    override fun editPreparationItem(item: Preparation) {
        db.addPreparationItem(item)
    }

    override fun deletePreparationItem(id: Int) {
        db.deletePreparationItem(id)
    }

    override fun deletePreparationAll() {
        db.deletePreparationAll()
    }
}