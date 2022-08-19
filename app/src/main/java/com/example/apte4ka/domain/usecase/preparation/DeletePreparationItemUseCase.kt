package com.example.apte4ka.domain.usecase.preparation

import com.example.apte4ka.domain.repostitory.preparation.PreparationRepository
import javax.inject.Inject

class DeletePreparationItemUseCase @Inject constructor(private val repository: PreparationRepository) {
    fun deletePreparationItem(id : Int){
        repository.deletePreparationItem(id)
    }
}