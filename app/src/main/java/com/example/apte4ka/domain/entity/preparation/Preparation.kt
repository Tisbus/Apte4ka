package com.example.apte4ka.domain.entity.preparation

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "preparation")
data class Preparation(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val aidKit: Int,
    val name: String,
    val symptoms: String,
    val packing: String,
    val description: String,
    val dataCreate: String,
    val dateExp: String,
)
