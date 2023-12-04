package com.maninmiddle.tests.presentation.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.maninmiddle.tests.R
import com.maninmiddle.tests.data.model.TaskModel
import com.maninmiddle.tests.data.model.UserData
import com.maninmiddle.tests.databinding.TasksCreateLayoutBinding
import java.util.Locale

class TasksCreateAdapter(
    private val context: Context, private val tasks: MutableList<TaskModel>
) : RecyclerView.Adapter<TasksCreateAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: TasksCreateLayoutBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            TasksCreateLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(view)
    }

    override fun getItemCount() = tasks.size

    override fun onBindViewHolder(
        holder: MyViewHolder, @SuppressLint("RecyclerView") position: Int
    ) {
        val task = tasks[position]

        holder.binding.tasksCreateItemTextLayout.hint = String.format(
            Locale.getDefault(), "%s %d", context.getString(R.string.stringTask), position + 1
        )
        holder.binding.tasksCreateItemText.setHintTextColor(context.getColor(R.color.colorWhite))

        holder.binding.tasksCreateItemRecycler.layoutManager = LinearLayoutManager(context)
        holder.binding.tasksCreateItemRecycler.adapter = VariantCreateAdapter(
            context, UserData.targetTest.tasks[position].variants, position
        )

        holder.binding.tasksCreateItemText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                UserData.targetTest.tasks[position].text = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        holder.binding.tasksCreateItemAddVariant.setOnClickListener {
            if (UserData.targetTest.tasks[position].variants.size >= 10) Toast.makeText(
                context, context.getString(R.string.stringVariantsLimit), Toast.LENGTH_SHORT
            ).show()
            else {
                UserData.targetTest.tasks[position].variants.add("")

                holder.binding.tasksCreateItemDeleteVariant.visibility = View.VISIBLE

                holder.binding.tasksCreateItemRecycler.adapter!!.notifyItemInserted(
                    UserData.targetTest.tasks[position].variants.size - 1
                )

            }
        }
        holder.binding.tasksCreateItemDeleteVariant.setOnClickListener {
            UserData.targetTest.tasks[position].variants.removeAt(
                UserData.targetTest.tasks[position].variants.size - 1
            )

            if (UserData.targetTest.tasks[position].variants.size == 2)
                holder.binding.tasksCreateItemDeleteVariant.visibility = View.GONE

            holder.binding.tasksCreateItemRecycler.adapter!!.notifyItemRemoved(UserData.targetTest.tasks[position].variants.size)
        }


    }
}