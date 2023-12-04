package com.maninmiddle.tests.presentation.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maninmiddle.tests.R
import com.maninmiddle.tests.data.model.UserData
import com.maninmiddle.tests.databinding.VariantCreateItemBinding
import java.util.Locale

class VariantCreateAdapter(
    private val context: Context,
    private val variants: MutableList<String>,
    private val taskIdx: Int
) : RecyclerView.Adapter<VariantCreateAdapter.ViewHolder>() {

    class ViewHolder(val binding: VariantCreateItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            VariantCreateItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.binding.variantsCreateItemText.setText("")
        holder.binding.variantsCreateItemCheckbox.isChecked = false

        holder.binding.variantsCreateItemTextLayout.hint =
            String.format(
                Locale.getDefault(),
                "%s %d",
                context.getString(R.string.stringVariant),
                position + 1
            )

        holder.binding.variantsCreateItemCheckbox.setOnCheckedChangeListener { _, checked ->
            if (checked)
                UserData.targetTest.tasks[taskIdx].rightVariants.add(position)
            else
                UserData.targetTest.tasks[taskIdx].rightVariants.remove(position)


        }

        holder.binding.variantsCreateItemText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                UserData.targetTest.tasks[taskIdx].variants[position] = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {}

        })
        holder.binding.variantsCreateItemText.setHintTextColor(context.getColor(R.color.colorWhite))
    }

    override fun getItemCount() = variants.size
}