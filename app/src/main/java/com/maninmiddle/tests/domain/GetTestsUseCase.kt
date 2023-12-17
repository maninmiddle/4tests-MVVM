package com.maninmiddle.tests.domain

import javax.inject.Inject

class GetTestsUseCase @Inject constructor(
    private val repository: TestsRepository
) {
    suspend fun getTests() = repository.getTests()
}