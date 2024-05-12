package com.example.mobileapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.example.mobileapp.model.TaskItem
import kotlinx.coroutines.flow.Flow


@Dao
interface TaskDao {
    @Upsert()
    suspend fun upsertTask(task: TaskItem)

    @Delete
    suspend fun deleteTask(task: TaskItem)

    @Query("SELECT * FROM TaskItem")
    fun getTasks(): Flow<List<TaskItem>>

    @Query("SELECT * FROM TaskItem ORDER BY id")
    fun getTasksByID(): Flow<List<TaskItem>>

    @Query("SELECT * FROM TaskItem ORDER BY priority")
    fun getTasksByPriority(): Flow<List<TaskItem>>
}
