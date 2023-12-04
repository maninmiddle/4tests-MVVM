package com.maninmiddle.tests.domain

import javax.inject.Inject

class GetTasksUseCase @Inject constructor(
    private val repository: TestsRepository,
    private val testId: Int
) {
    suspend operator fun invoke() = repository.getTasks(testId)
}