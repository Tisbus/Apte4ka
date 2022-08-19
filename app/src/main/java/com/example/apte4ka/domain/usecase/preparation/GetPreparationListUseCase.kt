package com.example.apte4ka.domain.usecase.preparation

import androidx.lifecycle.LiveData
import com.example.apte4ka.domain.entity.preparation.Preparation
import com.example.apte4ka.domain.repostitory.preparation.PreparationRepository
import javax.inject.Inject

class GetPreparationListUseCase @Inject constructor(private val repository: PreparationRepository) {
    fun getPreparationList() : LiveData<MutableList<Preparation>>{
        return repository.getPreparationList()
    }
}