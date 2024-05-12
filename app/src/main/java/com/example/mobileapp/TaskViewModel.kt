package com.example.mobileapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileapp.dao.TaskDao
import com.example.mobileapp.model.TaskItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class TaskViewModel(private val dao: TaskDao) : ViewModel() {
    private val _sortType = MutableStateFlow(SortType.ID)
    private val _state = MutableStateFlow(TaskState())
    private val _tasks = _sortType.flatMapLatest { sortType ->
        when (sortType) {
            SortType.ID -> dao.getTasksByID()
            SortType.PRIORITY -> dao.getTasksByPriority()
            SortType.TASKDATE -> dao.getTasks()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val state = combine(_state, _sortType, _tasks) { state, sortType, tasks ->
        state.copy(
            tasks = tasks,
            sortType = sortType,

            )

    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TaskState())

    fun onEvent(event: TaskEvent) {
        when (event) {
            is TaskEvent.DeleteTask -> {
                viewModelScope.launch {

                    dao.deleteTask(event.task)
                }
            }

            TaskEvent.HideDialog -> {
                _state.update { it.copy(isAddingTask = false) }
            }

            TaskEvent.SaveTask -> {
                val name = state.value.name
                val description = state.value.description
                val priority = state.value.priority
                val taskDate = state.value.taskDate
                if (name.isBlank() || description.isBlank() || taskDate.isBlank()) {
                    return
                }
                val task = TaskItem(
                    name = name,
                    description = description,
                    priority = priority,
                    taskDate = taskDate
                )
                viewModelScope.launch {
                    dao.upsertTask(task)
                }
                _state.update {
                    it.copy(
                        isAddingTask = false,
                        name = "", description = "", priority = 1, taskDate = ""
                    )
                }

            }

            is TaskEvent.SetDescription -> {
                _state.update {
                    it.copy(
                        description = event.description
                    )
                }
            }

            is TaskEvent.SetName -> {
                _state.update {
                    it.copy(
                        name = event.name
                    )
                }
            }

            is TaskEvent.SetPriority -> {
                _state.update {
                    it.copy(
                        priority = event.priority
                    )
                }
            }

            is TaskEvent.SetTaskDate -> {
                _state.update {
                    it.copy(
                        taskDate = event.taskDate
                    )
                }
            }

            TaskEvent.ShowDialog -> {
                _state.update { it.copy(isAddingTask = true) }
            }

            is TaskEvent.SortTask -> {
                _sortType.value = event.sortType
            }
        }
    }
}