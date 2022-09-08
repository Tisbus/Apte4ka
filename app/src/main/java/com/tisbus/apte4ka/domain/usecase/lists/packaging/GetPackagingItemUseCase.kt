package com.tisbus.apte4ka.domain.usecase.lists.packaging

import com.tisbus.apte4ka.domain.entity.packaging.Packaging
import com.tisbus.apte4ka.domain.repostitory.lists.ListsRepository
import javax.inject.Inject

class GetPackagingItemUseCase @Inject constructor(private val repository: ListsRepository) {
    suspend fun getPackagingItem(id : Int) : Packaging{
        return repository.getPackagingItem(id)
    }
}