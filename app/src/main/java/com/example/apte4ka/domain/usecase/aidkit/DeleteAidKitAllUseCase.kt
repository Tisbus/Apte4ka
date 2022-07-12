package com.example.apte4ka.domain.usecase.aidkit

import com.example.apte4ka.domain.repostitory.aidkit.AidKitRepository

class DeleteAidKitAllUseCase(private val repository: AidKitRepository) {
    fun deleteAidKitAll(){
        repository.deleteAidKitAll()
    }
}