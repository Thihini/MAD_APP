package com.example.mobileapp

import com.example.mobileapp.model.TaskItem

sealed interface TaskEvent {
    object SaveTask : TaskEvent
    data class SetName(val name: String) : TaskEvent
    data class SetDescription(val description: String) : TaskEvent
    data class SetPriority(val priority: Int) : TaskEvent
    data class SetTaskDate(val taskDate: String) : TaskEvent
    object ShowDialog : TaskEvent
    object HideDialog : TaskEvent
    data class SortTask(val sortType: SortType) : TaskEvent
    data class DeleteTask(val task: TaskItem) : TaskEvent
}