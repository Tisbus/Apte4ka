package com.example.apte4ka.domain.entity.packaging

data class Packaging (
    var id: Int,
    val name: String,
    val icon: String = "",
    var status: Boolean = DEFAULT_STATUS
) {
    companion object {
        const val DEFAULT_STATUS = false
    }
}