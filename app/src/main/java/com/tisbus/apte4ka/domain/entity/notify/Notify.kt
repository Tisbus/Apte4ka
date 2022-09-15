package com.tisbus.apte4ka.domain.entity.notify

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notify")
data class Notify(
    val name : String,
    val description : String,
    val icon : String,
    var idPrep : Int,
    var status : Boolean = ACTIVE_STATUS,
    @PrimaryKey(autoGenerate = true)
    val id : Int = UNDEFINED_ID
){
    companion object{
        const val UNDEFINED_ID = 0
        const val ACTIVE_STATUS = true
    }
}