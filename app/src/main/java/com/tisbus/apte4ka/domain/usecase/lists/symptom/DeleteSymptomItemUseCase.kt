package com.tisbus.apte4ka.domain.usecase.lists.symptom

import com.tisbus.apte4ka.domain.repostitory.lists.ListsRepository
import javax.inject.Inject

class DeleteSymptomItemUseCase @Inject constructor(private val repository: ListsRepository) {
    suspend fun deleteSymptomItem(id : Int){
        repository.deleteSymptomItem(id)
    }
}