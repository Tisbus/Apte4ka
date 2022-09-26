package com.tisbus.apte4ka.data.room.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tisbus.apte4ka.data.converter.Converter
import com.tisbus.apte4ka.data.room.aidkit.AidKitDao
import com.tisbus.apte4ka.data.room.lists.ListsDao
import com.tisbus.apte4ka.data.room.preparation.PreparationDao
import com.tisbus.apte4ka.domain.entity.aidkit.AidKit
import com.tisbus.apte4ka.domain.entity.packaging.Packaging
import com.tisbus.apte4ka.domain.entity.preparation.Preparation
import com.tisbus.apte4ka.domain.entity.symptom.Symptom

@Database(entities = [AidKit::class, Preparation::class, Symptom::class, Packaging::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class AidKitDatabase : RoomDatabase() {

    abstract fun aidKitDao(): AidKitDao
    abstract fun preparationDao(): PreparationDao
    abstract fun listsDao() : ListsDao

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