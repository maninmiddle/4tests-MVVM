package com.maninmiddle.tests.presentation.testCreate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maninmiddle.tests.data.model.TestModel
import com.maninmiddle.tests.data.repository.TestsRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestCreateViewModel @Inject constructor(
    private val repositoryImpl: TestsRepositoryImpl
) : ViewModel() {
    fun createAccount(testModel: TestModel) {
        viewModelScope.launch {
            repositoryImpl.createTest(testModel)
        }
    }
}