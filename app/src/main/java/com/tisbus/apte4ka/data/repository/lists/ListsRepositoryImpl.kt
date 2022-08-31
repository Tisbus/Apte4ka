package com.tisbus.apte4ka.data.repository.lists

import com.tisbus.apte4ka.data.lists.packaging.ListPackaging
import com.tisbus.apte4ka.data.lists.symptom.ListSymptom
import com.tisbus.apte4ka.domain.entity.packaging.Packaging
import com.tisbus.apte4ka.domain.entity.symptom.Symptom
import com.tisbus.apte4ka.domain.repostitory.lists.ListsRepository
import javax.inject.Inject

class ListsRepositoryImpl @Inject constructor() : ListsRepository {
    override fun getListSymptoms(): List<Symptom> {
        return ListSymptom().listSymptoms
    }

    override fun getListPackaging(): List<Packaging> {
        return ListPackaging().listPackaging
    }
}