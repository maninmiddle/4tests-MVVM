package com.maninmiddle.tests.domain

import javax.inject.Inject

class GetVariantsUseCase @Inject constructor(
    private val repository: TestsRepository,
) {
    suspend fun getVariants(taskId: Int) = repository.getVariants(taskId)
}