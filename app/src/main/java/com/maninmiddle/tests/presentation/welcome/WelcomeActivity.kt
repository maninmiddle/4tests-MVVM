package com.maninmiddle.tests.presentation.welcome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView.ItemAnimator
import com.maninmiddle.tests.R
import com.maninmiddle.tests.databinding.ActivityWelcomeBinding
import com.maninmiddle.tests.presentation.main.MainActivity
import com.maninmiddle.tests.presentation.testCreate.TestCreateActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.createTestBtn.setOnClickListener {
            val intent = Intent(this@WelcomeActivity, TestCreateActivity::class.java)
            startActivity(intent)
        }

        binding.enterTestBtn.setOnClickListener {
            val intent = Intent(this@WelcomeActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}