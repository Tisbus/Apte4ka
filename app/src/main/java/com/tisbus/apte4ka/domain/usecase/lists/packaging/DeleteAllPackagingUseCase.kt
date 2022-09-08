package com.tisbus.apte4ka.domain.usecase.lists.packaging

import com.tisbus.apte4ka.domain.repostitory.lists.ListsRepository
import javax.inject.Inject

class DeleteAllPackagingUseCase @Inject constructor(private val repository: ListsRepository) {
    suspend fun deleteAllPackaging(){
        repository.deleteAllPackaging()
    }
}