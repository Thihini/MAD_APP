package com.example.mobileapp.adapter;


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileapp.R
import com.example.mobileapp.model.TaskItem

class TaskAdapter(private val taskList: List<TaskItem>) :
    RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.task_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val taskItem = taskList[position]
        holder.taskHeading.text = taskItem.name
        holder.taskDate.text = taskItem.description
    }

    override fun getItemCount() = taskList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskHeading: TextView = itemView.findViewById(R.id.taskHeading)
        val taskDate: TextView = itemView.findViewById(R.id.taskDate)
    }
}

