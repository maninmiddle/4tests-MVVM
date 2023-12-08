package com.maninmiddle.tests.presentation.confirm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.maninmiddle.tests.R
import com.maninmiddle.tests.data.model.UserData
import com.maninmiddle.tests.databinding.ActivityTestCreateConfirmBinding
import com.maninmiddle.tests.presentation.welcome.WelcomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TestCreateConfirmActivity : AppCompatActivity() {
    private lateinit var viewModel: TestConfirmViewModel
    private lateinit var binding: ActivityTestCreateConfirmBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestCreateConfirmBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[TestConfirmViewModel::class.java]
        setContentView(binding.root)

        val test = UserData.targetTest

        binding.testConfirmName.text = test.name
        binding.testsConfirmDirection.text = test.subject
        binding.testsConfirmCompleteTime.text = test.completeTime.toString()
        binding.testsConfirmTasksCount.text = test.tasks.size.toString()

        binding.testsConfirmSubmit.setOnClickListener {
            viewModel.createTest(test)
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }
}