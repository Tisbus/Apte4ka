package com.tisbus.apte4ka.data.room.preparation

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tisbus.apte4ka.data.converter.Converter
import com.tisbus.apte4ka.domain.entity.preparation.Preparation

@Database(entities = [Preparation::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class PreparationDatabase : RoomDatabase() {

    abstract fun preparationDao(): PreparationDao

    companion object {
        private const val DB_NAME = "preparation"
        private val LOCK = Any()
        private var INSTANCE: PreparationDatabase? = null

        fun getInstance(application: Application): PreparationDatabase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(
                    application,
                    PreparationDatabase::class.java,
                    DB_NAME
                )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = db
                return db
            }
        }
    }
}