package com.tisbus.apte4ka.domain.entity.packaging

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "packaging")
data class Packaging (
    val name: String,
    val icon: String = "",
    var status: Boolean = DEFAULT_STATUS,
    @PrimaryKey(autoGenerate = true)
    var id: Int = UNDEFINED_ID
) {
    companion object {
        const val DEFAULT_STATUS = false
        const val UNDEFINED_ID = 0
    }
}