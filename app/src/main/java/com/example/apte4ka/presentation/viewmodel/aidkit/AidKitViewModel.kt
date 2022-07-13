package com.example.apte4ka.presentation.viewmodel.aidkit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.apte4ka.data.repository.aidkit.AidKitRepositoryImpl
import com.example.apte4ka.domain.entity.aidkit.AidKit
import com.example.apte4ka.domain.usecase.aidkit.AddAidKitItemUseCase
import com.example.apte4ka.domain.usecase.aidkit.GetAidKitListUseCase

class AidKitViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = AidKitRepositoryImpl(application)
    private val addAidKitItemUseCase = AddAidKitItemUseCase(repository)
    private val getAidKitListUseCase = GetAidKitListUseCase(repository)
/*    private val getAidKitItemUseCase = GetAidKitItemUseCase(repository)
    private val editAidKitItemUseCase = EditAidKitItemUseCase(repository)
    private val deleteAidKitAllUseCase = DeleteAidKitAllUseCase(repository)
    private val deleteAidKitItemUseCase = DeleteAidKitItemUseCase(repository)*/

    val listAidKit = getAidKitListUseCase.getAidKitList()

    fun addAidKit(
        name : String,
        description : String
    ){
        addAidKitItemUseCase.addAidKitItem(AidKit(name, description))
    }


}