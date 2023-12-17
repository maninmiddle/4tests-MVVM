package com.maninmiddle.tests.domain

import javax.inject.Inject

class GetTasksUseCase @Inject constructor(
    private val repository: TestsRepository,
) {
    suspend fun getTasks(testId: Int) = repository.getTasks(testId)
}