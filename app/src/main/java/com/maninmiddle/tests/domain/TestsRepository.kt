package com.maninmiddle.tests.domain

import com.maninmiddle.tests.data.model.MainModel
import com.maninmiddle.tests.data.model.TaskModel
import com.maninmiddle.tests.data.model.TestModel
import com.maninmiddle.tests.data.model.VariantModel
import retrofit2.Response

interface TestsRepository {
    suspend fun createTest(testModel: TestModel)

    suspend fun getTests(): Response<List<TestModel>>

    suspend fun getTasks(testId: Int): Response<List<TaskModel>>

    suspend fun getVariants(taskId: Int): Response<List<VariantModel>>

    suspend fun getTestById(id: Int): Response<TestModel>

}