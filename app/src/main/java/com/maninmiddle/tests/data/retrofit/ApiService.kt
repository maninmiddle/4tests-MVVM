package com.maninmiddle.tests.data.retrofit

import com.maninmiddle.tests.data.model.MainModel
import com.maninmiddle.tests.data.model.TaskModel
import com.maninmiddle.tests.data.model.TestModel
import com.maninmiddle.tests.data.model.VariantModel
import com.maninmiddle.tests.util.Constants.END_POINT_CREATE_TEST
import com.maninmiddle.tests.util.Constants.END_POINT_GET_TASKS
import com.maninmiddle.tests.util.Constants.END_POINT_GET_TESTS
import com.maninmiddle.tests.util.Constants.END_POINT_GET_TEST_BY_ID
import com.maninmiddle.tests.util.Constants.END_POINT_GET_VARIANTS
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET(END_POINT_GET_TESTS)
    suspend fun getTests(): Response<List<TestModel>>

    @GET(END_POINT_GET_TASKS)
    suspend fun getTasks(@Query("testId") testId: Int): Response<List<TaskModel>>

    @GET(END_POINT_GET_VARIANTS)
    suspend fun getVariants(@Query("taskId") taskId: Int): Response<List<VariantModel>>

    @GET(END_POINT_GET_TEST_BY_ID)
    suspend fun getTestById(@Query("testId") id: Int): Response<TestModel>

    @POST(END_POINT_CREATE_TEST)
    suspend fun createTest(@Body test: TestModel)
}