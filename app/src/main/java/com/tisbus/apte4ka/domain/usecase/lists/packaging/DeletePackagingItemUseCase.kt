package com.tisbus.apte4ka.domain.usecase.lists.packaging

import com.tisbus.apte4ka.domain.repostitory.lists.ListsRepository
import javax.inject.Inject

class DeletePackagingItemUseCase @Inject constructor(private val repository: ListsRepository) {
    suspend fun deletePackagingItem(id : Int){
        repository.deletePackagingItem(id)
    }
}