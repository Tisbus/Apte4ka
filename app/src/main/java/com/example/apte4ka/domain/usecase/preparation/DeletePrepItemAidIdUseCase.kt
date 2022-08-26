package com.example.apte4ka.domain.usecase.preparation

import com.example.apte4ka.domain.repostitory.preparation.PreparationRepository
import javax.inject.Inject

class DeletePrepItemAidIdUseCase@Inject constructor(private val repository: PreparationRepository) {
    fun deletePrepItemAidId(id : Int){
        repository.deletePrepItemAidId(id)
    }
}