package com.tisbus.apte4ka.domain.usecase.preparation

import com.tisbus.apte4ka.domain.entity.preparation.Preparation
import com.tisbus.apte4ka.domain.repostitory.preparation.PreparationRepository
import javax.inject.Inject

class AddPreparationItemUseCase @Inject constructor(private val repository: PreparationRepository) {
    fun addPreparationItem(item : Preparation){
        repository.addPreparationItem(item)
    }
}