package com.maninmiddle.tests.presentation.solve

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maninmiddle.tests.data.model.ApiState
import com.maninmiddle.tests.data.model.TaskModel
import com.maninmiddle.tests.data.model.VariantModel
import com.maninmiddle.tests.domain.GetTasksUseCase
import com.maninmiddle.tests.domain.GetVariantsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SolveViewModel @Inject constructor(
    private val getTasksUseCase: GetTasksUseCase,
    private val getVariantsUseCase: GetVariantsUseCase
) : ViewModel() {

    private val _responseTask = MutableStateFlow<ApiState<List<TaskModel>?>>(ApiState.Empty)
    val responseTask: StateFlow<ApiState<List<TaskModel>?>>
        get() = _responseTask

    private val _responseVariants = MutableStateFlow<ApiState<List<VariantModel>?>>(ApiState.Empty)
    val responseVariants: StateFlow<ApiState<List<VariantModel>?>>
        get() = _responseVariants

    private val _timerValue = MutableStateFlow<Long>(0)
    val timerValue: StateFlow<Long>
        get() = _timerValue

    private var _canContinue = MutableStateFlow(true)
    val canContinue: StateFlow<Boolean>
        get() = _canContinue

    private var countDownTimer: CountDownTimer? = null


    fun startTimer(mills: Long) {
        _canContinue.value = true
        countDownTimer = object : CountDownTimer(mills, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                _timerValue.value = millisUntilFinished
            }

            override fun onFinish() {
                _canContinue.value = true
            }


        }.start()

    }

    fun changeStateCanContinue() {
        _canContinue.value = !_canContinue.value
    }


    fun cancelTimer() {
        _canContinue.value = false
        countDownTimer?.cancel()
    }


    fun getTask(testId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getTasksUseCase.getTasks(testId).let { response ->
                if (response.isSuccessful) {
                    val data = response.body()
                    _responseTask.value = ApiState.Success(data)
                } else {
                    _responseTask.value = ApiState.Error(response.errorBody().toString())
                }
            }
        }
    }

    fun getVariants(taskId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getVariantsUseCase.getVariants(taskId).let { response ->
                if (response.isSuccessful) {
                    val data = response.body()
                    _responseVariants.value = ApiState.Success(data)
                } else {
                    _responseVariants.value = ApiState.Error(response.errorBody().toString())
                }
            }
        }
    }


}