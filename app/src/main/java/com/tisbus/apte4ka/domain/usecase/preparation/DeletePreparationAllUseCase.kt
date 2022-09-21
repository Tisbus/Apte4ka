package com.tisbus.apte4ka.domain.usecase.preparation

import com.tisbus.apte4ka.domain.repostitory.preparation.PreparationRepository
import javax.inject.Inject

class DeletePreparationAllUseCase @Inject constructor(private val repository: PreparationRepository) {
    suspend fun deletePreparationAll(){
        repository.deletePreparationAll()
    }
}