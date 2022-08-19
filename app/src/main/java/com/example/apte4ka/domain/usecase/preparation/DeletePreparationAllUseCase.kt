package com.example.apte4ka.domain.usecase.preparation

import com.example.apte4ka.domain.repostitory.preparation.PreparationRepository
import javax.inject.Inject

class DeletePreparationAllUseCase @Inject constructor(private val repository: PreparationRepository) {
    fun deletePreparationAll(){
        repository.deletePreparationAll()
    }
}