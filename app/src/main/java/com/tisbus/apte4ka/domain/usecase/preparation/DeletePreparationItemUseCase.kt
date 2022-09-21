package com.tisbus.apte4ka.domain.usecase.preparation

import com.tisbus.apte4ka.domain.repostitory.preparation.PreparationRepository
import javax.inject.Inject

class DeletePreparationItemUseCase @Inject constructor(private val repository: PreparationRepository) {
    suspend fun deletePreparationItem(id : Int){
        repository.deletePreparationItem(id)
    }
}