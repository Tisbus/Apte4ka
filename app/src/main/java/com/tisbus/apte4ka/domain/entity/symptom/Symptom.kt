package com.tisbus.apte4ka.domain.entity.symptom

data class Symptom(
    var id: Int,
    val name: String,
    val icon: String = "",
    var status: Boolean = DEFAULT_STATUS
) {
    companion object {
        const val DEFAULT_STATUS = false
    }
}
