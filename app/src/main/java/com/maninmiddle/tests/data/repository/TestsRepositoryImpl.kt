package com.maninmiddle.tests.data.repository

import com.maninmiddle.tests.data.model.TaskModel
import com.maninmiddle.tests.data.model.TestModel
import com.maninmiddle.tests.data.model.VariantModel
import com.maninmiddle.tests.data.retrofit.ApiService
import com.maninmiddle.tests.domain.TestsRepository
import retrofit2.Response
import javax.inject.Inject

class TestsRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : TestsRepository {
    override suspend fun createTest(testModel: TestModel): Response<String> {
        return apiService.createTest(testModel)
    }

    override suspend fun getTests(): Response<List<TestModel>> {
        return apiService.getTests()
    }

    override suspend fun getTasks(testId: Int): Response<List<TaskModel>> {
        return apiService.getTasks(testId)
    }

    override suspend fun getVariants(taskId: Int): Response<List<VariantModel>> {
        return apiService.getVariants(taskId)
    }

    override suspend fun getTestById(id: Int): Response<TestModel> {
        return apiService.getTestById(id)
    }

    override suspend fun createTask(taskModel: TaskModel): Response<String> {
        return apiService.createTask(taskModel)
    }

    override suspend fun createVariant(variantModel: VariantModel): Response<String> {
        return apiService.createVariant(variantModel)
    }
}