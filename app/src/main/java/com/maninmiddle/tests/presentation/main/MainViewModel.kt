package com.maninmiddle.tests.presentation.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maninmiddle.tests.data.model.ApiState
import com.maninmiddle.tests.data.model.TestModel
import com.maninmiddle.tests.domain.GetTestByIdUseCase
import com.maninmiddle.tests.domain.GetTestsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTestsUseCase: GetTestsUseCase,
    private val getTestByIdUseCase: GetTestByIdUseCase
) : ViewModel() {

    private val _foundTest = MutableStateFlow<TestModel?>(
        TestModel(
            name = "",
            subject = "",
            completeTime = 0,
            password = "",
            tasks = mutableListOf()
        )
    )
    val foundTest: StateFlow<TestModel?>
        get() = _foundTest

    private val _responseTests = MutableStateFlow<ApiState<List<TestModel>?>>(
        ApiState.Empty
    )
    val responseTests: StateFlow<ApiState<Any?>>
        get() = _responseTests


    fun getTestById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getTestByIdUseCase.getTestById(id).let { res ->
                if (res.isSuccessful) {
                    _foundTest.value = res.body()

                } else {
                    Log.e("GetTestById", "${res.errorBody()}")
                }
            }

        }
    }

    fun getTests() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getTestsUseCase.getTests().let { response ->
                    if (response.isSuccessful) {
                        val data = response.body()
                        _responseTests.value = ApiState.Success(data)
                    } else {
                        _responseTests.value = ApiState.Error(response.errorBody().toString())
                    }
                }
            } catch (e: Exception) {
                _responseTests.value = ApiState.Error("error")
            }

        }
    }

}