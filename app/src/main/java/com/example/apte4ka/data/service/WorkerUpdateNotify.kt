package com.example.apte4ka.data.service

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.apte4ka.R
import com.example.apte4ka.domain.entity.preparation.Preparation
import com.example.apte4ka.presentation.fragment.PreparationDetailFragment
import java.text.SimpleDateFormat
import java.util.*

class WorkerUpdateNotify(context: Context, workerParams: WorkerParameters) : Worker(
    context,
    workerParams
) {

    override fun doWork(): Result {
        //for normal work need inject repository with help dagger inject
/*        Log.i("workCheck", "start work")
        val repository = PreparationRepositoryImpl(Application())
        Log.i("workCheck", repository.getPreparationList().value?.size.toString())
        repository.getPreparationList().value?.forEach { i ->
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
        }*/
        return Result.success()
    }

    private fun setupIntentDetail(i: Preparation): PendingIntent? {
        val intent =
            Intent(applicationContext, PreparationDetailFragment::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }.putExtra(DETAIL_PREP_ID, i.id)
        return PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
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

    companion object {
        const val DETAIL_PREP_ID = "detail_prep_id"
        var NOTIFICATION_ID = 1
        const val CHANNEL_ID = "1"
    }
}