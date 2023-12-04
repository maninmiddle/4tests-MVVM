package com.maninmiddle.tests.presentation.result

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.addCallback
import com.maninmiddle.tests.R
import com.maninmiddle.tests.databinding.ActivityResultBinding
import com.maninmiddle.tests.presentation.main.MainActivity
import com.maninmiddle.tests.presentation.welcome.WelcomeActivity
import com.maninmiddle.tests.util.Constants.RECEIVE_RIGHT_ANSWERS
import com.maninmiddle.tests.util.Constants.RECEIVE_TASKS_COUNT
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpViews()
        onBackPressedDispatcher.addCallback(this) {
            handleBackPressed()
        }

        binding.ivBackArrow.setOnClickListener {
            val intent = Intent(this@ResultActivity, WelcomeActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }

    private fun handleBackPressed() {
        val intent = Intent(this@ResultActivity, WelcomeActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }


    private fun setUpViews() {
        val rightAnswers = (intent.getStringExtra(RECEIVE_RIGHT_ANSWERS))!!.toInt()
        val tasksCount = (intent.getStringExtra(RECEIVE_TASKS_COUNT))!!.toInt()

        binding.testsResultMark.text =
            ((rightAnswers.toFloat() / tasksCount.toFloat()) * 10).toInt().toString()

        binding.testsResultRightAnswers.text =
            getString(R.string.stringRightAnswers, rightAnswers.toString())

        binding.testsResultTasks.text =
            getString(R.string.stringTotalTasksCount, tasksCount.toString())
    }


}