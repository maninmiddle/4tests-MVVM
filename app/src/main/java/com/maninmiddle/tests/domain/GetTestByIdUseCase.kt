package com.maninmiddle.tests.domain

import javax.inject.Inject

class GetTestByIdUseCase @Inject constructor(
    private val repository: TestsRepository
) {
    suspend fun getTestById(id: Int) = repository.getTestById(id)
}