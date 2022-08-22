package com.example.apte4ka.data.repository.preparation

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.example.apte4ka.data.room.preparation.PreparationDao
import com.example.apte4ka.data.worker.WorkerUpdateNotify
import com.example.apte4ka.domain.entity.preparation.Preparation
import com.example.apte4ka.domain.repostitory.preparation.PreparationRepository
import javax.inject.Inject

class PreparationRepositoryImpl @Inject constructor(
    private val db: PreparationDao,
    private val application: Application,
) : PreparationRepository {

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

    override fun updateNotify() {
        WorkManager.getInstance(application)
            .enqueueUniqueWork(
                WorkerUpdateNotify.NAME_WORKER,
                ExistingWorkPolicy.REPLACE,
                WorkerUpdateNotify.makeRequest()
            )
    }
}