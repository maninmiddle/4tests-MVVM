package com.maninmiddle.tests.presentation.adapters

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import android.view.animation.TranslateAnimation
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.maninmiddle.tests.R
import com.maninmiddle.tests.data.model.TestModel
import com.maninmiddle.tests.databinding.ContinueDialogBinding
import com.maninmiddle.tests.databinding.PasswordDialogBinding
import com.maninmiddle.tests.databinding.TestsItemLayoutBinding
import com.maninmiddle.tests.presentation.solve.SolveActivity
import com.maninmiddle.tests.util.Constants.RECEIVE_COMPLETE_TIME
import com.maninmiddle.tests.util.Constants.RECEIVE_TEST_ID

class TestsAdapter(
    private val tests: List<TestModel>,
    private val context: Context
) : RecyclerView.Adapter<TestsAdapter.TestsViewHolder>() {

    class TestsViewHolder(val binding: TestsItemLayoutBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestsViewHolder {
        val view = TestsItemLayoutBinding.inflate(
            LayoutInflater.from(
                parent.context
            ),
            parent,
            false
        )
        return TestsViewHolder(view)
    }

    override fun onBindViewHolder(holder: TestsViewHolder, position: Int) {
        val test = tests[position]
        holder.binding.cvLayout.setOnClickListener {
            if (test.password.isEmpty()) {
                showContinueDialog(test)
            } else {
                showPasswordDialog(test)
            }
        }


        holder.binding.testName.text = test.name
        holder.binding.testCompleteTime.text = test.completeTime.toString()
        holder.binding.testSubject.text = test.subject

    }

    private fun showPasswordDialog(test: TestModel) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = PasswordDialogBinding.inflate(inflater)
        dialog.setContentView(binding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.tvTestName.text = test.name
        binding.btnNo.setOnClickListener {
            dialog.cancel()
        }
        binding.btnYes.setOnClickListener {
            if (binding.etPassword.text.isEmpty()) {
                binding.etPassword.error = context.getString(R.string.string_password_not_written)
            } else if (binding.etPassword.text.toString() != test.password) {
                binding.etPassword.error = context.getString(R.string.string_password_incorrect)
            } else {
                dialog.cancel()
                startSolveActivity(test)
            }
        }
        dialog.show()
    }

    private fun showContinueDialog(test: TestModel) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = ContinueDialogBinding.inflate(inflater)
        dialog.setContentView(binding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.tvTestName.text = test.name
        binding.tvTestTime.text = test.completeTime.toString()
        binding.btnNo.setOnClickListener {
            dialog.cancel()
        }
        binding.btnYes.setOnClickListener {
            dialog.cancel()
            startSolveActivity(test)
        }

        dialog.show()


    }

    private fun startSolveActivity(test: TestModel) {
        val intent = Intent(context, SolveActivity::class.java)
        intent.putExtra(RECEIVE_TEST_ID, test.id.toString())
        intent.putExtra(RECEIVE_COMPLETE_TIME, test.completeTime.toString())
        context.startActivity(intent)
        (context as Activity).finish()
    }

    override fun getItemCount() = tests.size

}