package com.example.apte4ka.domain.usecase.aidkit

import com.example.apte4ka.domain.repostitory.aidkit.AidKitRepository
import javax.inject.Inject

class DeleteAidKitItemUseCase @Inject constructor(private val repository: AidKitRepository) {
    suspend fun deleteAidKitItem(id : Int){
        repository.deleteAidKitItem(id)
    }
}