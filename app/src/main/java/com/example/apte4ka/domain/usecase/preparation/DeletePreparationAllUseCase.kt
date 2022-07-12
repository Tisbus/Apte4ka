package com.example.apte4ka.domain.usecase.preparation

import com.example.apte4ka.domain.repostitory.preparation.PreparationRepository

class DeletePreparationAllUseCase(private val repository: PreparationRepository) {
    fun deletePreparationAll(){
        repository.deletePreparationAll()
    }
}