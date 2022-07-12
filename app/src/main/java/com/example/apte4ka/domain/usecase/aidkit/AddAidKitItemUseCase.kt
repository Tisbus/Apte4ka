package com.example.apte4ka.domain.usecase.aidkit

import com.example.apte4ka.domain.entity.aidkit.AidKit
import com.example.apte4ka.domain.repostitory.aidkit.AidKitRepository

class AddAidKitItemUseCase(private val repository: AidKitRepository) {
    fun addAidKitItem(item : AidKit){
        repository.addAidKitItem(item)
    }
}