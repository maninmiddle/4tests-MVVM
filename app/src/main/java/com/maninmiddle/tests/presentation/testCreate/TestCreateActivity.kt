package com.maninmiddle.tests.presentation.testCreate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import com.maninmiddle.tests.R
import com.maninmiddle.tests.data.model.TaskModel
import com.maninmiddle.tests.data.model.TestModel
import com.maninmiddle.tests.data.model.UserData
import com.maninmiddle.tests.databinding.ActivityTestCreateBinding
import com.maninmiddle.tests.presentation.tasks.TasksCreateActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TestCreateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTestCreateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.ivBackArrow.setOnClickListener {
            finish()
        }

        binding.testCreateContinue.setOnClickListener {
            validateFields()
        }


    }

    private fun validateFields() {
        val name = binding.testNameEt.text.toString()
        val completeTime = binding.completeTimeEt.text.toString()
        val subject = binding.testSubjectEt.text.toString()
        val password = binding.passwordEt.text.toString()
        when {
            name.isEmpty() ->
                binding.testNameEt.error = getString(R.string.string_empty_name)

            completeTime.isEmpty() ->
                binding.completeTimeEt.error = getString(R.string.string_empty_completeTime)

            subject.isEmpty() ->
                binding.testSubjectEt.error = getString(R.string.string_empty_subject)

            else -> {
                UserData.targetTest = TestModel(
                    name,
                    subject,
                    completeTime.toInt(),
                    password,
                    mutableListOf(
                        TaskModel("", mutableListOf("", ""), mutableListOf())
                    )
                )
                val intent = Intent(this@TestCreateActivity, TasksCreateActivity::class.java)
                startActivity(intent)
            }
        }


    }
}
