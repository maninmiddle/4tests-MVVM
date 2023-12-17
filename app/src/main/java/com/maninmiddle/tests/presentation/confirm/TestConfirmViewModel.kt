package com.maninmiddle.tests.presentation.confirm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maninmiddle.tests.data.model.TaskModel
import com.maninmiddle.tests.data.model.TestModel
import com.maninmiddle.tests.data.model.UserData
import com.maninmiddle.tests.data.model.VariantModel
import com.maninmiddle.tests.data.repository.TestsRepositoryImpl
import com.maninmiddle.tests.domain.CreateTaskUseCase
import com.maninmiddle.tests.domain.CreateTestUseCase
import com.maninmiddle.tests.domain.CreateVariantUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestConfirmViewModel @Inject constructor(
    private val createTestUseCase: CreateTestUseCase,
    private val createTaskUseCase: CreateTaskUseCase,
    private val createVariantUseCase: CreateVariantUseCase
) : ViewModel() {


    fun createTest(testModel: TestModel) {
        viewModelScope.launch(Dispatchers.IO) {
            createTestUseCase.createTest(testModel).let { testResponse ->
                val testId = testResponse.body()!!
                for (task in testModel.tasks) {
                    task.testId = testId.toInt()
                    createTaskUseCase.createTask(
                        task
                    ).let { taskResponse ->
                        var variantNum = 0
                        for (variant in task.variants) {
                            createVariantUseCase.createVariant(
                                VariantModel(
                                    taskResponse.body()!!.toInt(),
                                    variant,
                                    if (task.rightVariants.contains(variantNum)) 1 else 0
                                )
                            )
                            variantNum++
                        }
                    }

                }
            }
        }

    }
}