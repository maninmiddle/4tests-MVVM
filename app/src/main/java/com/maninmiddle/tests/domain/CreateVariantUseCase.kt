package com.maninmiddle.tests.domain

import com.maninmiddle.tests.data.model.VariantModel
import javax.inject.Inject

class CreateVariantUseCase @Inject constructor(
    private val repository: TestsRepository,
) {
    suspend fun createVariant(variantModel: VariantModel) = repository.createVariant(variantModel)
}