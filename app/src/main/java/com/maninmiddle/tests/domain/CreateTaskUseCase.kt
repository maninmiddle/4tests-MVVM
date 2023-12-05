package com.maninmiddle.tests.domain

import com.maninmiddle.tests.data.model.TaskModel
import javax.inject.Inject

class CreateTaskUseCase @Inject constructor(
    private val repository: TestsRepository,
    private val taskModel: TaskModel
) {
    suspend operator fun invoke() = repository.createTask(taskModel)
}