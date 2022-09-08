package com.tisbus.apte4ka.data.room.lists

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tisbus.apte4ka.domain.entity.packaging.Packaging
import com.tisbus.apte4ka.domain.entity.symptom.Symptom

@Database(entities = [Symptom::class, Packaging::class], version = 1, exportSchema = false)
abstract class ListsDatabase : RoomDatabase() {

    abstract fun listsDao() : ListsDao

    companion object{
        private var INSTANCE : ListsDatabase? = null
        private const val DB_NAME = "lists"
        private val LOCK = Any()

        fun getInstance(application: Application) : ListsDatabase{
            INSTANCE?.let{
                return it
            }
            synchronized(LOCK){
                INSTANCE?.let{
                    return it
                }
                val db = Room.databaseBuilder(
                    application,
                    ListsDatabase::class.java,
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