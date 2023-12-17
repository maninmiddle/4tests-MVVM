package com.maninmiddle.tests.domain

import com.maninmiddle.tests.data.model.TestModel
import javax.inject.Inject

class CreateTestUseCase @Inject constructor(
    private val repository: TestsRepository,
) {
    suspend fun createTest(testModel: TestModel) = repository.createTest(testModel)
}