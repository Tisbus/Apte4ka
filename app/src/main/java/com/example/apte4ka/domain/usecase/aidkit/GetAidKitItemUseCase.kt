package com.example.apte4ka.domain.usecase.aidkit

import com.example.apte4ka.domain.entity.aidkit.AidKit
import com.example.apte4ka.domain.repostitory.aidkit.AidKitRepository
import javax.inject.Inject

class GetAidKitItemUseCase @Inject constructor(private val repository: AidKitRepository) {
    suspend fun getAidKitItem(id : Int) : AidKit{
        return repository.getAidKitItem(id)
    }
}