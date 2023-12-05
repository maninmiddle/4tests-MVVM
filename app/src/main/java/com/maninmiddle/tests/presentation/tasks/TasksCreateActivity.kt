package com.maninmiddle.tests.presentation.tasks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.maninmiddle.tests.R
import com.maninmiddle.tests.data.model.TaskModel
import com.maninmiddle.tests.data.model.UserData
import com.maninmiddle.tests.databinding.ActivityTasksCreateBinding
import com.maninmiddle.tests.presentation.adapters.TasksCreateAdapter
import com.maninmiddle.tests.presentation.confirm.TestCreateConfirmActivity
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

        binding.ivBackArrow.setOnClickListener {
            finish()
        }

        binding.tasksCreateAddTask.setOnClickListener {
            if (UserData.targetTest.tasks.size >= 50) {
                Toast.makeText(
                    this@TasksCreateActivity,
                    getString(R.string.string_limit_tasks),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                if (currentFocus != null) {
                    currentFocus!!.clearFocus()
                }

                UserData.targetTest.tasks.add(
                    TaskModel("", mutableListOf("", ""), mutableListOf())
                )

                binding.rvLayout.adapter!!.notifyItemInserted(UserData.targetTest.tasks.size - 1)
            }
        }

        binding.tasksCreateContinue.setOnClickListener {
            var canContinue = true

            for (task in UserData.targetTest.tasks) {
                if (!canContinue) {
                    break
                }
                if (task.text.isEmpty()) {
                    Toast.makeText(
                        this,
                        getString(R.string.stringTaskNoText),
                        Toast.LENGTH_SHORT
                    ).show()

                    canContinue = false
                }

                for (variant in task.variants) {
                    if (variant.isEmpty() && canContinue) {
                        Toast.makeText(
                            this,
                            getString(R.string.stringVariantNotText),
                            Toast.LENGTH_SHORT
                        ).show()
                        canContinue = false
                    }
                }
                if (task.rightVariants.isEmpty() && canContinue) {
                    Toast.makeText(
                        this,
                        getString(R.string.stringTaskNoRightVariant),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            if (canContinue) {
                val intent = Intent(this, TestCreateConfirmActivity::class.java)
                startActivity(intent)
            }


        }

    }
}