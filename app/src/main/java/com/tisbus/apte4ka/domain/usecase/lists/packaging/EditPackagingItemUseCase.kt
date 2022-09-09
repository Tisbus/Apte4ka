package com.tisbus.apte4ka.domain.usecase.lists.packaging

import com.tisbus.apte4ka.domain.entity.packaging.Packaging
import com.tisbus.apte4ka.domain.repostitory.lists.ListsRepository
import javax.inject.Inject

class EditPackagingItemUseCase @Inject constructor(private val repository: ListsRepository) {
    suspend fun editPackagingItem(item: Packaging) {
        repository.editPackagingItem(item)
    }
}