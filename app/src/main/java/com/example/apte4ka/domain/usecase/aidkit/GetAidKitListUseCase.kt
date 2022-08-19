package com.example.apte4ka.domain.usecase.aidkit

import androidx.lifecycle.LiveData
import com.example.apte4ka.domain.entity.aidkit.AidKit
import com.example.apte4ka.domain.repostitory.aidkit.AidKitRepository
import javax.inject.Inject

class GetAidKitListUseCase @Inject constructor(private val repository: AidKitRepository) {
    fun getAidKitList() : LiveData<MutableList<AidKit>>{
        return repository.getAidKitList()
    }
}