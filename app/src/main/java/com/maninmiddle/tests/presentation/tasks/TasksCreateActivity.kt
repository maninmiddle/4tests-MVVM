package com.maninmiddle.tests.presentation.tasks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.maninmiddle.tests.R
import com.maninmiddle.tests.data.model.UserData
import com.maninmiddle.tests.databinding.ActivityTasksCreateBinding
import com.maninmiddle.tests.presentation.adapters.TasksCreateAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TasksCreateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTasksCreateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTasksCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvLayout.layoutManager = LinearLayoutManager(this)
        binding.rvLayout.adapter =
            TasksCreateAdapter(this@TasksCreateActivity, UserData.targetTest.tasks)
    }
}