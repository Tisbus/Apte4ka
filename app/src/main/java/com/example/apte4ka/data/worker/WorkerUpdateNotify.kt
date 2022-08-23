package com.example.apte4ka.data.worker

import android.app.PendingIntent
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.os.bundleOf
import androidx.navigation.NavDeepLinkBuilder
import androidx.navigation.Navigation
import androidx.work.*
import com.example.apte4ka.R
import com.example.apte4ka.data.room.preparation.PreparationDao
import com.example.apte4ka.domain.entity.preparation.Preparation
import com.example.apte4ka.presentation.fragment.PreparationDetailFragment
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class WorkerUpdateNotify(
    context: Context,
    workerParams: WorkerParameters,
    private val prepDao: PreparationDao,
) : CoroutineWorker(
    context,
    workerParams
) {


    override suspend fun doWork(): Result {
        try {
            val list = prepDao.getPreparationL()
            //for normal work need inject repository with help dagger inject
            Log.i("workCheck", "start work")
            Log.i("workCheck", list.size.toString())
            list.forEach { i ->
                Log.i("workCheck", i.name)
                if (getCountDayToEnd(i.dateExp).toInt() <= 30) {
                    val goToDetail = setupIntentDetail(i)
                    val numberOfDays = getCountDayToEnd(i.dateExp).toInt()
                    val namePrep = i.name
                    val textExpDate = String.format(
                        "%s - срок годности заканчивается, осталось - %s д.",
                        namePrep,
                        numberOfDays.toString()
                    )
                    setupNotificationBuilder(goToDetail, textExpDate)
                }
            }
        } catch (e: Exception) {}
        return Result.success()
    }

    companion object {
        const val DETAIL_PREP_ID = "detail_prep_id"
        var NOTIFICATION_ID = 1
        const val CHANNEL_ID = "1"

        const val NAME_WORKER = "WorkerUpdateNotify"

        fun makeRequest(): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<WorkerUpdateNotify>()
                .setInitialDelay(workerGetTime(), TimeUnit.MILLISECONDS)
                .build()
        }

        private fun workerGetTime(): Long {
            val currentDate = Calendar.getInstance()
            val dueDate = Calendar.getInstance()
            // Set Execution around 05:00:00 AM
            dueDate.set(Calendar.HOUR_OF_DAY, 12)
            dueDate.set(Calendar.MINUTE, 57)
            dueDate.set(Calendar.SECOND, 30)
            if (dueDate.before(currentDate)) {
                dueDate.add(Calendar.HOUR_OF_DAY, 24)
            }
            return dueDate.timeInMillis.minus(currentDate.timeInMillis)
        }
    }

    class Factory @Inject constructor(
        private val prepDao: PreparationDao,
    ) : ChildWorkerFactory {
        override fun create(appContext: Context, params: WorkerParameters): ListenableWorker {
            return WorkerUpdateNotify(
                appContext,
                params,
                prepDao
            )
        }
    }

    private fun setupIntentDetail(i: Preparation): PendingIntent {
        val bundle = bundleOf(DETAIL_PREP_ID to i.id)
        return NavDeepLinkBuilder(applicationContext)
            .setGraph(R.navigation.main_navigation)
            .setDestination(R.id.preparationDetailFragment)
            .setArguments(bundle)
            .createPendingIntent()
    }

    private fun setupNotificationBuilder(
        goToDetail: PendingIntent?,
        textExpDate: String,
    ) {
        val builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setContentTitle("Apte4ka")
            .setContentText(textExpDate)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(goToDetail)
            .setAutoCancel(true)
            .build()
        NotificationManagerCompat.from(applicationContext)
            .notify(NOTIFICATION_ID++, builder)
    }

    //for api max 26
    private fun getCountDayToEnd(endDate: String): Long {
        val fmt = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val toDay = Date()
        val eDate = fmt.parse(endDate)
        val milliseconds = eDate?.time?.minus(toDay.time)
        val days = (milliseconds?.div(1000) ?: throw RuntimeException("div to zero")).div(3600)
            .div(24)
        Log.i("Time", days.toString())
        return days
    }
}