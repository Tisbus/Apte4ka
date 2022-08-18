package com.example.apte4ka.presentation.activity

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.apte4ka.R
import com.example.apte4ka.data.service.WorkerUpdateNotify
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        workerUpdateNotification()
        setContentView(R.layout.activity_main)
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Срок годности"
            val descText = "Каннал уведомлений о сроках годности"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
                .apply { description = descText }
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE)
                    as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun workerGetTime(): Long {
        val currentDate = Calendar.getInstance()
        val dueDate = Calendar.getInstance()
        // Set Execution around 05:00:00 AM
        dueDate.set(Calendar.HOUR_OF_DAY, 20)
        dueDate.set(Calendar.MINUTE, 34)
        dueDate.set(Calendar.SECOND, 0)
        if (dueDate.before(currentDate)) {
            dueDate.add(Calendar.HOUR_OF_DAY, 24)
        }
        return dueDate.timeInMillis.minus(currentDate.timeInMillis)
    }

    private fun workerUpdateNotification() {
        val dailyWorkRequest = OneTimeWorkRequestBuilder<WorkerUpdateNotify>()
            .setInitialDelay(workerGetTime(), TimeUnit.SECONDS)
            .build()
        WorkManager.getInstance(applicationContext)
            .enqueue(dailyWorkRequest)
    }

    companion object {
        var NOTIFICATION_ID = 1
        const val CHANNEL_ID = "1"
    }
}