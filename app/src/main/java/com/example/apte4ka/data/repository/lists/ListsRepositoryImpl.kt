package com.example.apte4ka.data.repository.lists

import com.example.apte4ka.data.lists.symptom.ListSymptom
import com.example.apte4ka.domain.entity.symptom.Symptom
import com.example.apte4ka.domain.repostitory.lists.ListsRepository

class ListsRepositoryImpl() : ListsRepository {
    override fun getListSymptoms(): List<Symptom> {
        return ListSymptom().listSymptoms
    }
}