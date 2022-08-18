package com.example.apte4ka.domain.repostitory.aidkit

import androidx.lifecycle.LiveData
import com.example.apte4ka.domain.entity.aidkit.AidKit

interface AidKitRepository {
    fun getAidKitList(): LiveData<MutableList<AidKit>>
    suspend fun getAidKitItem(id: Int): AidKit
    suspend fun addAidKitItem(item: AidKit)
    suspend fun editAidKitItem(item: AidKit)
    suspend fun deleteAidKitItem(id: Int)
    suspend fun deleteAidKitAll()
}