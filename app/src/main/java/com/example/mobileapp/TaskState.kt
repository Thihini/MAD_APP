package com.example.mobileapp

import androidx.compose.runtime.State
import com.example.mobileapp.model.TaskItem

data class TaskState(
    val tasks: List<TaskItem> = emptyList(),
    val name: String = "",
    val description: String = "",
    val priority: Int = 1,
    val taskDate: String = "",
    val sortType: SortType = SortType.ID,
    val isAddingTask: Boolean = false,
)
