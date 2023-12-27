package com.maninmiddle.tests.presentation.main

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.maninmiddle.tests.R
import com.maninmiddle.tests.data.model.ApiState
import com.maninmiddle.tests.data.model.TestModel
import com.maninmiddle.tests.data.model.isDefault
import com.maninmiddle.tests.databinding.ActivityMainBinding
import com.maninmiddle.tests.databinding.GetTestByIdDialogBinding
import com.maninmiddle.tests.presentation.adapters.TestsAdapter
import com.maninmiddle.tests.presentation.solve.SolveActivity
import com.maninmiddle.tests.util.Constants
import com.maninmiddle.tests.util.Constants.RECEIVE_COMPLETE_TIME
import com.maninmiddle.tests.util.Constants.RECEIVE_TEST_ID
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private var currentJob: Job? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBackArrow.setOnClickListener {
            finish()
        }
        binding.ivSearch.setOnClickListener {
            startTestById()
        }

        viewModel.getTests()

        response()
    }

    private fun startTestById() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val dialogBinding = GetTestByIdDialogBinding.inflate(inflater)
        dialog.setContentView(dialogBinding.root)

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))



        dialog.show()

        dialogBinding.btnEnterTest.setOnClickListener {
            viewModel.getTestById(dialogBinding.etTestId.text.toString().toInt())
            currentJob?.cancel()
            currentJob = lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.foundTest.collect { test ->
                        if (test != null && !test.isDefault()) {
                            Log.d("MainActivity", "$test")
                            val intent = Intent(this@MainActivity, SolveActivity::class.java)
                            intent.putExtra(RECEIVE_TEST_ID, test.id.toString())
                            intent.putExtra(RECEIVE_COMPLETE_TIME, test.completeTime.toString())
                            dialog.cancel()
                            startActivity(intent)
                            finish()
                        } else {
                            Log.d("MainActivity", "Server return null")
                            dialog.cancel()

                        }
                    }
                }

            }

        }

        dialogBinding.btnNo.setOnClickListener {
            dialog.cancel()
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.getTests()
    }


    private fun response() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.responseTests.collect { apiState ->
                    when (apiState) {
                        is ApiState.Success -> {
                            val data = apiState.data
                            if (data is List<*>) {
                                val testModelData = data.filterIsInstance<TestModel>()
                                rvSetup(testModelData)
                            }
                        }

                        is ApiState.Error -> {
                            binding.ivSad.visibility = View.VISIBLE
                            binding.tvError.visibility = View.VISIBLE
                        }

                        else -> {
                            // not used
                        }
                    }

                }

            }
        }

    }

    private fun rvSetup(data: List<TestModel>?) {
        val adapter = TestsAdapter(data!!, this@MainActivity)
        binding.rvLayout.adapter = adapter
        binding.rvLayout.layoutManager = LinearLayoutManager(this@MainActivity)
    }
}