package com.tisbus.apte4ka.data.worker

import android.app.PendingIntent
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore.Images.Media.getBitmap
import android.provider.MediaStore.Images.Media.getContentUri
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.os.bundleOf
import androidx.navigation.NavDeepLinkBuilder
import androidx.preference.PreferenceManager
import androidx.work.*
import com.tisbus.apte4ka.R
import com.tisbus.apte4ka.data.room.preparation.PreparationDao
import com.tisbus.apte4ka.domain.entity.preparation.Preparation
import com.tisbus.apte4ka.presentation.activity.MainActivity
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
            val sharedP = PreferenceManager.getDefaultSharedPreferences(applicationContext)
            val dayCustom = sharedP.getString("first_end", "30")?.toInt()
                val list = prepDao.getPreparationL()
                list.forEach { i ->
                    if (getCountDayToEnd(i.dateExp).toInt() <= dayCustom!! && !i.customStatus) {
                        setupCustomExpDate(i, STATUS_NAME_CUSTOM)
                    }
                    if (getCountDayToEnd(i.dateExp).toInt() <= 1 && !i.oneDayStatus) {
                        setupCustomExpDate(i, STATUS_NAME_ONE_DAY)
                    }
                }
        }catch (e: Exception) {
        }
        return Result.success()
    }

    private fun setupCustomExpDate(i: Preparation, status: String) {
        val goToDetail = setupIntentDetail(i)
        val numberOfDays = getCountDayToEnd(i.dateExp).toInt()
        val namePrep = i.name
        val text = when (status) {
            STATUS_NAME_CUSTOM -> "%s - срок годности заканчивается, осталось - %s д."
            STATUS_NAME_ONE_DAY -> "%s - срок годности заканчился - %s д."
            else -> "%s - срок годности заканчивается, осталось - %s д."
        }
        val textExpDate = String.format(
            text,
            namePrep,
            numberOfDays.toString()
        )
        val bitmap = getBitmap(applicationContext.contentResolver, Uri.parse(i.image))
        setupNotificationBuilder(goToDetail, textExpDate, bitmap)
        val item = when (status) {
            STATUS_NAME_CUSTOM -> i.copy(customStatus = true)
            STATUS_NAME_ONE_DAY -> i.copy(oneDayStatus = true)
            else -> i.copy(customStatus = true)
        }
        prepDao.addPreparationItem(item)
    }

    companion object {
        const val DETAIL_PREP_ID = "detail_prep_id"
        var NOTIFICATION_ID = 1
        const val CHANNEL_ID = "1"
        const val STATUS_NAME_CUSTOM = "custom"
        const val STATUS_NAME_ONE_DAY = "oneDay"
        const val NAME_WORKER = "WorkerUpdateNotify"

        fun makeRequest(long: Long): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<WorkerUpdateNotify>()
                .setInitialDelay(long, TimeUnit.MILLISECONDS)
                .build()
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
            .setComponentName(MainActivity::class.java)
            .setArguments(bundle)
            .createPendingIntent()
    }

    private fun setupNotificationBuilder(
        goToDetail: PendingIntent?,
        textExpDate: String,
        icon: Bitmap,
    ) {
        val builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setContentTitle("Apte4ka")
            .setContentText(textExpDate)
            .setLargeIcon(icon)
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