package com.tisbus.apte4ka.data.room.aidkit

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tisbus.apte4ka.domain.entity.aidkit.AidKit

@Database(entities = [AidKit::class], version = 3, exportSchema = false)
abstract class AidKitDatabase : RoomDatabase() {

    abstract fun aidKitDao(): AidKitDao

    companion object {
        private var INSTANCE: AidKitDatabase? = null
        private const val DB_NAME = "aid_kit"
        private val LOCK = Any()

        fun getInstance(application: Application) : AidKitDatabase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK){
                INSTANCE?.let{
                    return it
                }
                val db = Room.databaseBuilder(
                    application,
                    AidKitDatabase::class.java,
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