package com.tisbus.apte4ka.data.room.notify

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tisbus.apte4ka.domain.entity.notify.Notify

@Database(entities = [Notify::class], version = 1, exportSchema = false)
abstract class NotifyDatabase : RoomDatabase() {

    abstract fun notifyDao(): NotifyDao

    companion object {
        private const val DB_NAME = " notify"
        private val LOCK = Any()
        private var INSTANCE: NotifyDatabase? = null

        fun getInstance(application: Application): NotifyDatabase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(
                    application,
                    NotifyDatabase::class.java,
                    DB_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = db
                return db
            }
        }
    }
}