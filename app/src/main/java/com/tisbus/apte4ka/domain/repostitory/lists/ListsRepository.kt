package com.tisbus.apte4ka.domain.repostitory.lists

import com.tisbus.apte4ka.domain.entity.packaging.Packaging
import com.tisbus.apte4ka.domain.entity.symptom.Symptom

interface ListsRepository {
    fun getListSymptoms() : List<Symptom>
    fun getListPackaging() : List<Packaging>
}