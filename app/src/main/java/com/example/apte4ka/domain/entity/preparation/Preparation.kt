package com.example.apte4ka.domain.entity.preparation

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "preparation")
data class Preparation(
    val aidKit: Int,
    val name: String,
    val image : String,
    val symptoms: String,
    val packing: String,
    val description: String,
    val dataCreate: String,
    val dateExp: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = UNDEFINED_ID
){
    companion object{
        const val UNDEFINED_ID = 0
    }
}
