package com.example.apte4ka.domain.usecase.lists

import com.example.apte4ka.domain.entity.packaging.Packaging
import com.example.apte4ka.domain.entity.symptom.Symptom
import com.example.apte4ka.domain.repostitory.lists.ListsRepository
import javax.inject.Inject

class GetPackagingListUseCase @Inject constructor(private val repository: ListsRepository) {
    fun getPackagingList(): List<Packaging> {
        return repository.getListPackaging()
    }
}