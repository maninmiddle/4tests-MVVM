package com.maninmiddle.tests.domain

import com.maninmiddle.tests.data.model.MainModel
import com.maninmiddle.tests.data.model.TaskModel
import com.maninmiddle.tests.data.model.TestModel
import com.maninmiddle.tests.data.model.VariantModel
import retrofit2.Response

interface TestsRepository {
    suspend fun createTest(testModel: TestModel): Response<String>

    suspend fun getTests(): Response<List<TestModel>>

    suspend fun getTasks(testId: Int): Response<List<TaskModel>>

    suspend fun getVariants(taskId: Int): Response<List<VariantModel>>

    suspend fun getTestById(id: Int): Response<TestModel>

    suspend fun createTask(taskModel: TaskModel): Response<String>

    suspend fun createVariant(variantModel: VariantModel): Response<String>

}