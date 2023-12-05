package com.maninmiddle.tests.domain

import com.maninmiddle.tests.data.model.VariantModel
import javax.inject.Inject

class CreateVariantUseCase @Inject constructor(
    private val repository: TestsRepository,
    private val variantModel: VariantModel
) {
    suspend operator fun invoke() = repository.createVariant(variantModel)
}