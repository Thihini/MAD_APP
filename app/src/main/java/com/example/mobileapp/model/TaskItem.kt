package com.example.mobileapp.model;

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskItem(
    val name: String,
    val description: String,
    val priority: Int,
    val taskDate: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
)
