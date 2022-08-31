package com.tisbus.apte4ka.domain.usecase.preparation

import com.tisbus.apte4ka.domain.repostitory.preparation.PreparationRepository
import javax.inject.Inject

class DeletePrepItemAidIdUseCase@Inject constructor(private val repository: PreparationRepository) {
    fun deletePrepItemAidId(id : Int){
        repository.deletePrepItemAidId(id)
    }
}