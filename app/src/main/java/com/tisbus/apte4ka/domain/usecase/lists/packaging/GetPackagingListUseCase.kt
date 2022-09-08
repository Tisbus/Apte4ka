package com.tisbus.apte4ka.domain.usecase.lists.packaging

import androidx.lifecycle.LiveData
import com.tisbus.apte4ka.domain.entity.packaging.Packaging
import com.tisbus.apte4ka.domain.repostitory.lists.ListsRepository
import javax.inject.Inject

class GetPackagingListUseCase @Inject constructor(private val repository: ListsRepository) {
    fun getPackagingList(): LiveData<MutableList<Packaging>> {
        return repository.getListPackaging()
    }
}