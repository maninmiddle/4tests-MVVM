package com.maninmiddle.tests.domain

import javax.inject.Inject

class GetVariantsUseCase @Inject constructor(
    private val repository: TestsRepository,
    private val taskId: Int
) {
    suspend operator fun invoke() = repository.getVariants(taskId)
}