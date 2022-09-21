package com.tisbus.apte4ka.data.repository.preparation

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.preference.PreferenceManager
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.tisbus.apte4ka.data.room.preparation.PreparationDao
import com.tisbus.apte4ka.data.worker.WorkerUpdateNotify
import com.tisbus.apte4ka.domain.entity.preparation.Preparation
import com.tisbus.apte4ka.domain.repostitory.preparation.PreparationRepository
import java.util.*
import javax.inject.Inject

class PreparationRepositoryImpl @Inject constructor(
    private val db: PreparationDao,
    private val application: Application,
) : PreparationRepository {

    override fun getPreparationList(): LiveData<MutableList<Preparation>> = db.getPreparationList()

    override suspend fun getPreparationItem(id: Int): Preparation {
        return db.getPreparationItem(id)
    }

    override suspend fun addPreparationItem(item: Preparation) {
        db.addPreparationItem(item)
    }

    override suspend fun editPreparationItem(item: Preparation) {
        db.addPreparationItem(item)
    }

    override suspend fun copyPreparationItem(item: Preparation) {
        db.addPreparationItem(item)
    }

    override suspend fun deletePreparationItem(id: Int) {
        db.deletePreparationItem(id)
    }

    override suspend fun deletePrepItemAidId(id: Int) {
        db.deletePrepItemAidId(id)
    }

    override suspend fun deletePreparationAll() {
        db.deletePreparationAll()
    }

    override fun updateNotify() {
        WorkManager.getInstance(application)
            .enqueueUniqueWork(
                WorkerUpdateNotify.NAME_WORKER,
                ExistingWorkPolicy.REPLACE,
                WorkerUpdateNotify.makeRequest(workerGetTime())
            )
    }

    private fun workerGetTime(): Long {
        val currentDate = Calendar.getInstance()
        val dieDate = Calendar.getInstance()
        //setting is pref
        val pref = PreferenceManager.getDefaultSharedPreferences(application)
        val dateCustom = pref.getString("notify_time", "9:30")?.split(":")?.toTypedArray()
        val hour = dateCustom?.get(0)?.toInt()
        val minute = dateCustom?.get(1)?.toInt()
        if (hour != null && minute != null) {
            dieDate.set(Calendar.HOUR_OF_DAY, hour)
            dieDate.set(Calendar.MINUTE, minute)
        }
        dieDate.set(Calendar.SECOND, 0)
        if (dieDate.before(currentDate)) {
            dieDate.add(Calendar.HOUR_OF_DAY, 24)
        }
        return dieDate.timeInMillis.minus(currentDate.timeInMillis)
    }
}