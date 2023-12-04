package com.maninmiddle.tests.domain

import com.maninmiddle.tests.data.model.TestModel
import javax.inject.Inject

class CreateTestUseCase @Inject constructor(
    private val repository: TestsRepository,
    private val testModel: TestModel
) {
    suspend operator fun invoke() = repository.createTest(testModel)
}