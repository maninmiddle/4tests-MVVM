package com.maninmiddle.tests.presentation.main

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maninmiddle.tests.data.model.ApiState
import com.maninmiddle.tests.data.model.TestModel
import com.maninmiddle.tests.data.repository.TestsRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repositoryImpl: TestsRepositoryImpl
) : ViewModel() {
    private val _responseTests = MutableStateFlow<ApiState<List<TestModel>?>>(
        ApiState.Empty
    )
    val responseTests: StateFlow<ApiState<Any?>>
        get() = _responseTests


    fun getTests() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repositoryImpl.getTests().let { response ->
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