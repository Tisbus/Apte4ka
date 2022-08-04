package com.example.apte4ka.domain.entity.symptom

data class Symptom(
    val name: String,
    val icon: String = "",
    var status: Boolean = DEFAULT_STATUS,
    var id: Int = UNDEFINED_ID,
) {
    companion object {
        const val UNDEFINED_ID = -1
        const val DEFAULT_STATUS = false
    }
}
