package com.example.apte4ka.domain.usecase.preparation

import com.example.apte4ka.domain.entity.preparation.Preparation
import com.example.apte4ka.domain.repostitory.preparation.PreparationRepository

class GetPreparationItemUseCase(private val repository: PreparationRepository) {
    fun getPreparationItem(id : Int) : Preparation{
        return repository.getPreparationItem(id)
    }
}