package com.maninmiddle.tests.di

import com.maninmiddle.tests.data.repository.TestsRepositoryImpl
import com.maninmiddle.tests.data.retrofit.ApiService
import com.maninmiddle.tests.domain.TestsRepository
import com.maninmiddle.tests.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object TestsModule {


    @Provides
    fun provideTestsRepository(apiService: ApiService): TestsRepository {
        return TestsRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(): ApiService =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

}