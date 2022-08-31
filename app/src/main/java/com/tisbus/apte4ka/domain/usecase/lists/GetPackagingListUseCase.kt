package com.tisbus.apte4ka.domain.usecase.lists

import com.tisbus.apte4ka.domain.entity.packaging.Packaging
import com.tisbus.apte4ka.domain.repostitory.lists.ListsRepository
import javax.inject.Inject

class GetPackagingListUseCase @Inject constructor(private val repository: ListsRepository) {
    fun getPackagingList(): List<Packaging> {
        return repository.getListPackaging()
    }
}