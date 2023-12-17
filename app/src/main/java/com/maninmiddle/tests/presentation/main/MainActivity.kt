package com.maninmiddle.tests.presentation.main

import android.os.Bundle
import android.util.Log
import android.view.View
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
import com.maninmiddle.tests.databinding.ActivityMainBinding
import com.maninmiddle.tests.presentation.adapters.TestsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBackArrow.setOnClickListener {
            finish()
        }

        viewModel.getTests()

        response()
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