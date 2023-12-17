package com.maninmiddle.tests.domain

import com.maninmiddle.tests.data.model.TaskModel
import javax.inject.Inject

class CreateTaskUseCase @Inject constructor(
    private val repository: TestsRepository,
) {
    suspend fun createTask(taskModel: TaskModel) = repository.createTask(taskModel)
}