package com.example.apte4ka.domain.usecase.preparation

import com.example.apte4ka.domain.entity.preparation.Preparation
import com.example.apte4ka.domain.repostitory.preparation.PreparationRepository
import javax.inject.Inject

class CopyPreparationItemUseCase @Inject constructor(private val repository: PreparationRepository) {
    fun copyPreparationItem(item : Preparation){
        repository.copyPreparationItem(item)
    }
}