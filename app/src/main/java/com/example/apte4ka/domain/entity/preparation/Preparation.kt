package com.example.apte4ka.domain.entity.preparation

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.apte4ka.domain.entity.symptom.Symptom

@Entity(tableName = "preparation")
data class Preparation(
    val aidKit: Int,
    val name: String,
    val image : String,
    val symptoms: MutableList<Symptom>,
    val packing: String,
    val description: String,
    val dataCreate: String,
    val dateExp: String,
    val customStatus : Boolean = false,
    val oneDayStatus : Boolean = false,
    @PrimaryKey(autoGenerate = true)
    val id: Int = UNDEFINED_ID
){
    companion object{
        const val UNDEFINED_ID = 0
    }
}
