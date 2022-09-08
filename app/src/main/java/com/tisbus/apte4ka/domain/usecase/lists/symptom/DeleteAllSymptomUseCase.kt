package com.tisbus.apte4ka.domain.usecase.lists.symptom

import com.tisbus.apte4ka.domain.repostitory.lists.ListsRepository
import javax.inject.Inject

class DeleteAllSymptomUseCase @Inject constructor(private val repository: ListsRepository) {
    suspend fun deleteAllSymptom(){
        repository.deleteAllSymptom()
    }
}