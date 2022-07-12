package com.example.apte4ka.domain.usecase.aidkit

import androidx.lifecycle.LiveData
import com.example.apte4ka.domain.entity.aidkit.AidKit
import com.example.apte4ka.domain.repostitory.aidkit.AidKitRepository

class GetAidKitListUseCase(private val repository: AidKitRepository) {
    fun getAidKitList() : LiveData<MutableList<AidKit>>{
        return repository.getAidKitList()
    }
}