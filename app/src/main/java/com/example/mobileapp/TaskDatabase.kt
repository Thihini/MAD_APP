package com.example.mobileapp

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mobileapp.dao.TaskDao
import com.example.mobileapp.model.TaskItem

@Database(
    entities = [TaskItem::class],
    version = 1
)

abstract class TaskDatabase:RoomDatabase() {
    abstract  val dao : TaskDao
}