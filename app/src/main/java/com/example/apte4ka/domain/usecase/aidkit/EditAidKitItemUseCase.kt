package com.example.apte4ka.domain.usecase.aidkit

import com.example.apte4ka.domain.entity.aidkit.AidKit
import com.example.apte4ka.domain.repostitory.aidkit.AidKitRepository

class EditAidKitItemUseCase(private val repository: AidKitRepository) {
    fun editAidKitItem(item : AidKit){
        repository.editAidKitItem(item)
    }
}