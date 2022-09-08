package com.tisbus.apte4ka.domain.usecase.lists.symptom

import com.tisbus.apte4ka.domain.entity.symptom.Symptom
import com.tisbus.apte4ka.domain.repostitory.lists.ListsRepository
import javax.inject.Inject

class GetSymptomItemUseCase @Inject constructor(private val repository: ListsRepository) {
    suspend fun getSymptomItem(id : Int) : Symptom{
        return repository.getSymptomItem(id)
    }
}