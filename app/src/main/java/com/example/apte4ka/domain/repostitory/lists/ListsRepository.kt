package com.example.apte4ka.domain.repostitory.lists

import com.example.apte4ka.domain.entity.symptom.Symptom

interface ListsRepository {
    fun getListSymptoms() : List<Symptom>
}