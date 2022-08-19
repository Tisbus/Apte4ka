package com.example.apte4ka.data.repository.preparation

import androidx.lifecycle.LiveData
import com.example.apte4ka.data.room.preparation.PreparationDao
import com.example.apte4ka.domain.entity.preparation.Preparation
import com.example.apte4ka.domain.repostitory.preparation.PreparationRepository
import javax.inject.Inject

class PreparationRepositoryImpl @Inject constructor(private val db : PreparationDao) : PreparationRepository {

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

    override fun copyPreparationItem(item: Preparation) {
        db.addPreparationItem(item)
    }

    override fun deletePreparationItem(id: Int) {
        db.deletePreparationItem(id)
    }

    override fun deletePreparationAll() {
        db.deletePreparationAll()
    }
}