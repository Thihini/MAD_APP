package com.example.mobileapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun AddTaskDialog(
    state: TaskState,
    onEvent: (TaskEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = {
            onEvent(TaskEvent.HideDialog)
        },
        title = { Text(text = "Add contact") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = state.name,
                    onValueChange = {
                        onEvent(TaskEvent.SetName(it))
                    },
                    placeholder = {
                        Text(text = "Task Name")
                    }
                )
                TextField(
                    value = state.description,
                    onValueChange = {
                        onEvent(TaskEvent.SetDescription(it))
                    },
                    placeholder = {
                        Text(text = "Description")
                    }
                )
                DatePickerView(state,onEvent)
                PriorityDropdown(
                    priority = state.priority,
                    onPrioritySelected = { selectedPriority ->
                        onEvent(TaskEvent.SetPriority(selectedPriority))
                    }
                )
            }
        },
        buttons = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Button(onClick = {
                    onEvent(TaskEvent.SaveTask)
                }) {
                    Text(text = "Save")
                }
            }
        }
    )
}

@Composable
fun PriorityDropdown(
    priority: Int,
    onPrioritySelected: (Int) -> Unit
) {
    val priorities = listOf("Low", "Medium", "High")
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(priority) }

    Box(modifier = Modifier.fillMaxWidth()) {
        Column {
            Text(text = "Priority")
            OutlinedButton(
                onClick = { expanded = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = priorities[selectedIndex])
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                priorities.forEachIndexed { index, priority ->
                    DropdownMenuItem(onClick = {
                        selectedIndex = index
                        onPrioritySelected(selectedIndex)
                        expanded = false
                    }) {
                        Text(text = priority)
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerView(   state: TaskState,
                      onEvent: (TaskEvent) -> Unit,) {
    val datePickerState = rememberDatePickerState(selectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            return utcTimeMillis <= System.currentTimeMillis()
        }
    })
    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    }
    onEvent(TaskEvent.SetTaskDate("$selectedDate"))
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        DatePicker(
            state = datePickerState
        )

    }
}

private fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("yyy/MM/dd")
    return formatter.format(Date(millis))
}