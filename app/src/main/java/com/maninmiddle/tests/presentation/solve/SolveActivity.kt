package com.maninmiddle.tests.presentation.solve

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.maninmiddle.tests.R
import com.maninmiddle.tests.data.model.ApiState
import com.maninmiddle.tests.data.model.TaskModel
import com.maninmiddle.tests.data.model.VariantModel
import com.maninmiddle.tests.databinding.ActivitySolveBinding
import com.maninmiddle.tests.presentation.adapters.TestSolveAdapter
import com.maninmiddle.tests.presentation.result.ResultActivity
import com.maninmiddle.tests.util.Constants.RECEIVE_COMPLETE_TIME
import com.maninmiddle.tests.util.Constants.RECEIVE_RIGHT_ANSWERS
import com.maninmiddle.tests.util.Constants.RECEIVE_TASKS_COUNT
import com.maninmiddle.tests.util.Constants.RECEIVE_TEST_ID
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SolveActivity : AppCompatActivity() {
    private var currentTask = 0
    private var rightAnswers = 0
    private lateinit var viewModel: SolveViewModel
    private lateinit var binding: ActivitySolveBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[SolveViewModel::class.java]
        binding = ActivitySolveBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Toast.makeText(
                    this@SolveActivity,
                    getString(R.string.stringTestExit),
                    Toast.LENGTH_SHORT
                ).show()
                viewModel.cancelTimer()
                finish()
            }

        })

        val testId = intent.getStringExtra(RECEIVE_TEST_ID)
        val completeTime = intent.getStringExtra(RECEIVE_COMPLETE_TIME)!!.toLong() * 1000

        viewModel.getTask(testId!!.toInt())

        viewModel.startTimer(completeTime)

        lifecycleScope.launch {
            responseTask()
        }

        responseVariants()
        observeTime()


    }


    fun answer(right: Boolean) {
        if (right) {
            rightAnswers++
        }
        currentTask++
        lifecycleScope.launch {
            responseTask()
        }

        responseVariants()
    }


    private fun observeTime() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.timerValue.collect { mills ->
                    val seconds = mills / 1000
                    val minutes = seconds / 60
                    binding.countDownTimer.text =
                        getString(R.string.string_time_format, minutes, seconds)
                }
            }
        }
    }


    private fun responseVariants() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.responseVariants.collect { apiState ->
                    when (apiState) {
                        is ApiState.Success -> {
                            val data = apiState.data
                            if (data is List<*>) {
                                val variantsModelData = data.filterIsInstance<VariantModel>()
                                rvSetup(variantsModelData)
                            }
                        }

                        is ApiState.Error -> {
                            Log.e("responseError", apiState.errorMessage)
                        }

                        else -> Log.e("responseError", "Something wrong")
                    }
                }
            }
        }
    }

    private fun rvSetup(data: List<VariantModel>?) {
        val adapter = TestSolveAdapter(this@SolveActivity, data!!)
        binding.variantsRvLayout.adapter = adapter
        binding.variantsRvLayout.layoutManager = LinearLayoutManager(this)
    }

    private fun responseTask() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.responseTask.collect { apiState ->
                    when (apiState) {
                        is ApiState.Success -> {
                            val data = apiState.data
                            if (data is List<*>) {
                                val taskModelData = data.filterIsInstance<TaskModel>()
                                checkTasks(taskModelData)
                                viewModel.canContinue.collect { canContinue ->
                                    if (!canContinue) {
                                        val intent =
                                            Intent(this@SolveActivity, ResultActivity::class.java)
                                        intent.putExtra(
                                            RECEIVE_RIGHT_ANSWERS,
                                            rightAnswers.toString()
                                        )
                                        val testId = taskModelData[0].testId
                                        intent.putExtra(
                                            RECEIVE_TASKS_COUNT,
                                            taskModelData.size.toString()
                                        )
                                        intent.putExtra(
                                            RECEIVE_TEST_ID,
                                            testId.toString()
                                        )
                                        viewModel.cancelTimer()
                                        startActivity(intent)
                                        finish()
                                    }
                                }

                            }
                        }

                        is ApiState.Error -> {
                            Log.e("responseError", apiState.errorMessage)
                        }

                        else -> Log.e("responseError", "Something wrong")
                    }
                }
            }
        }

    }

    private fun checkTasks(taskModelData: List<TaskModel>) {
        if (taskModelData.size == currentTask) {
            val intent =
                Intent(this@SolveActivity, ResultActivity::class.java)
            intent.putExtra(RECEIVE_RIGHT_ANSWERS, rightAnswers.toString())
            val testId = taskModelData[currentTask - 1].testId
            intent.putExtra(
                RECEIVE_TASKS_COUNT,
                taskModelData.size.toString()
            )
            intent.putExtra(
                RECEIVE_TEST_ID,
                testId.toString()
            )
            viewModel.cancelTimer()
            startActivity(intent)
            finish()
        } else {
            binding.tvTaskText.text = taskModelData[currentTask].text
            viewModel.getVariants(taskModelData[currentTask].id)
        }
    }


}
