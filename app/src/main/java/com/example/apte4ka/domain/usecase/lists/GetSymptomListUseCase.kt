package com.example.apte4ka.domain.usecase.lists

import com.example.apte4ka.domain.entity.symptom.Symptom
import com.example.apte4ka.domain.repostitory.lists.ListsRepository

class GetSymptomListUseCase(private val repository: ListsRepository) {
    fun getSymptomList(): List<Symptom> {
        return repository.getListSymptoms()
    }
}