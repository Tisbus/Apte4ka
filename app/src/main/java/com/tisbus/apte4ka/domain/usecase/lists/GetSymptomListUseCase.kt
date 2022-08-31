package com.tisbus.apte4ka.domain.usecase.lists

import com.tisbus.apte4ka.domain.entity.symptom.Symptom
import com.tisbus.apte4ka.domain.repostitory.lists.ListsRepository
import javax.inject.Inject

class GetSymptomListUseCase @Inject constructor(private val repository: ListsRepository) {
    fun getSymptomList(): List<Symptom> {
        return repository.getListSymptoms()
    }
}