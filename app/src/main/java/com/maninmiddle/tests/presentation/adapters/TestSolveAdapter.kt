package com.maninmiddle.tests.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.maninmiddle.tests.data.model.TaskModel
import com.maninmiddle.tests.data.model.VariantModel
import com.maninmiddle.tests.databinding.TestSolveItemBinding
import com.maninmiddle.tests.presentation.solve.SolveActivity

class TestSolveAdapter(
    private val context: Context,
    private val variants: List<VariantModel>
) : RecyclerView.Adapter<TestSolveAdapter.SolveViewHolder>() {
    class SolveViewHolder(val binding: TestSolveItemBinding) : ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SolveViewHolder {
        val view = TestSolveItemBinding.inflate(
            LayoutInflater.from(
                parent.context
            ),
            parent,
            false
        )
        return SolveViewHolder(view)
    }


    override fun onBindViewHolder(holder: SolveViewHolder, position: Int) {
        val binding = holder.binding
        val variant = variants[position]
        binding.variantTv.text = variant.text

        binding.variant.setOnClickListener {

            val context = (context as SolveActivity)
            if (variant.isRight == 1)
                context.answer(true)
            else
                context.answer(false)

        }

    }


    override fun getItemCount() = variants.size
}