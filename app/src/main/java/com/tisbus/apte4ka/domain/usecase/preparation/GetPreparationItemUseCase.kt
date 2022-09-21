package com.tisbus.apte4ka.domain.usecase.preparation

import com.tisbus.apte4ka.domain.entity.preparation.Preparation
import com.tisbus.apte4ka.domain.repostitory.preparation.PreparationRepository
import javax.inject.Inject

class GetPreparationItemUseCase @Inject constructor(private val repository: PreparationRepository) {
    suspend fun getPreparationItem(id : Int) : Preparation{
        return repository.getPreparationItem(id)
    }
}