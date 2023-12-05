package com.maninmiddle.tests.presentation.confirm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.maninmiddle.tests.R
import com.maninmiddle.tests.data.model.UserData
import com.maninmiddle.tests.databinding.ActivityTestCreateConfirmBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TestCreateConfirmActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTestCreateConfirmBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestCreateConfirmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val test = UserData.targetTest

        binding.testConfirmName.text = test.name
        binding.testsConfirmDirection.text = test.subject
        binding.testsConfirmCompleteTime.text = test.completeTime.toString()
        binding.testsConfirmTasksCount.text = test.tasks.size.toString()

        binding.testsConfirmSubmit.setOnClickListener {

        }
    }
}