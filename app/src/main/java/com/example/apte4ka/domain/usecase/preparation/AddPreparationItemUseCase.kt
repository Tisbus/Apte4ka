package com.example.apte4ka.domain.usecase.preparation

import com.example.apte4ka.domain.entity.preparation.Preparation
import com.example.apte4ka.domain.repostitory.preparation.PreparationRepository

class AddPreparationItemUseCase(private val repository: PreparationRepository) {
    fun addPreparationItem(item : Preparation){
        repository.addPreparationItem(item)
    }
}