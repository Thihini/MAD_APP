package com.example.mobileapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.plcoding.roomguideandroid.ui.theme.RoomGuideAndroidTheme


class MainActivity : AppCompatActivity() {


    private val db by lazy {
        Room.databaseBuilder(
            applicationContext, TaskDatabase::class.java, "tasksdatabase.db"
        ).build()
    }

    private val viewModel by viewModels<TaskViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return TaskViewModel(db.dao) as T
                }
            }
        })


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//            setContentView(R.layout.activity_main)
        setContent {
            RoomGuideAndroidTheme {
                val state by viewModel.state.collectAsState()
                TaskScreen(state = state, onEvent = viewModel::onEvent)
            }
        }

    }




}