package com.tisbus.apte4ka.domain.entity.symptom

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "symptom")
data class Symptom(
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
