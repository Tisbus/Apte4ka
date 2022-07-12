package com.example.apte4ka.domain.entity.aidkit

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "aid_kit")
data class AidKit(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val name : String,
    val description : String
)
