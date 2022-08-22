package com.example.apte4ka.domain.usecase.preparation

import com.example.apte4ka.domain.repostitory.preparation.PreparationRepository
import javax.inject.Inject

class UpdateNotificationUseCase @Inject constructor(
    private val repository: PreparationRepository
) {
    operator fun invoke() = repository.updateNotify()
}