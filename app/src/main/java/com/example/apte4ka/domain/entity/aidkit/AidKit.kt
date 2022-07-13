package com.example.apte4ka.domain.entity.aidkit

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "aid_kit")
data class AidKit(
    val name : String,
    val description : String,
    @PrimaryKey(autoGenerate = true)
    val id : Int = UNDEFINED_ID
){
    companion object{
        const val UNDEFINED_ID = 0
    }
}
