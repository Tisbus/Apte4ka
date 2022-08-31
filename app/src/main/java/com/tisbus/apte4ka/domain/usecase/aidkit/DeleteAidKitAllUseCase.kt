package com.tisbus.apte4ka.domain.usecase.aidkit

import com.tisbus.apte4ka.domain.repostitory.aidkit.AidKitRepository
import javax.inject.Inject

class DeleteAidKitAllUseCase @Inject constructor(private val repository: AidKitRepository) {
    suspend fun deleteAidKitAll(){
        repository.deleteAidKitAll()
    }
}