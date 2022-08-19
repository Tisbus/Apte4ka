package com.example.apte4ka.domain.usecase.aidkit

import com.example.apte4ka.domain.entity.aidkit.AidKit
import com.example.apte4ka.domain.repostitory.aidkit.AidKitRepository
import javax.inject.Inject

class AddAidKitItemUseCase @Inject constructor(private val repository: AidKitRepository) {
    suspend fun addAidKitItem(item : AidKit){
        repository.addAidKitItem(item)
    }
}