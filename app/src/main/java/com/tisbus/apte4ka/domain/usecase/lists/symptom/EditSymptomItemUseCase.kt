package com.tisbus.apte4ka.domain.usecase.lists.symptom

import com.tisbus.apte4ka.domain.entity.symptom.Symptom
import com.tisbus.apte4ka.domain.repostitory.lists.ListsRepository
import javax.inject.Inject

class EditSymptomItemUseCase @Inject constructor(private val repository: ListsRepository) {
    suspend fun editSymptomItem(item: Symptom) {
        repository.editSymptomItem(item)
    }
}