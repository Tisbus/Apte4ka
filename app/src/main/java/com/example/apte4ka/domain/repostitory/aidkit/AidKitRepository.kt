package com.example.apte4ka.domain.repostitory.aidkit

import androidx.lifecycle.LiveData
import com.example.apte4ka.domain.entity.aidkit.AidKit

interface AidKitRepository {
    fun getAidKitList() : LiveData<MutableList<AidKit>>
    fun getAidKitItem(id : Int) : AidKit
    fun addAidKitItem(item : AidKit)
    fun editAidKitItem(item : AidKit)
    fun deleteAidKitItem(id : Int)
    fun deleteAidKitAll()
}