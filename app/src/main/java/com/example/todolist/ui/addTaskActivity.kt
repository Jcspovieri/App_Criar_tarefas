package com.example.todolist.ui

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.databinding.ActivityAddTaskBinding
import com.example.todolist.datasource.TaskDataSource
import com.example.todolist.extension.format
import com.example.todolist.extension.text
import com.example.todolist.model.Task
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*
import javax.security.auth.login.LoginException
import kotlin.math.log

class addTaskActivity:AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra(TASK_ID)){
            val taskId = intent.getIntExtra(TASK_ID, 0)
            TaskDataSource.findById(taskId)?.let {
                binding.tilTitle.text =it.title
                binding.tilDate.text = it.date
                binding.talHour.text = it.hour
            }
        }

        insertListeners()

    }

    private fun insertListeners() {
        binding.tilDate.editText?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.addOnPositiveButtonClickListener {
                val timeZone = TimeZone.getDefault()
                val offset = timeZone.getOffset(Date().time) * -1
                binding.tilDate.text =Date(it + offset).format()

            }
            datePicker.show(supportFragmentManager,"DATE_PICKER_TAG")
       }
        binding.talHour.editText?.setOnClickListener{
            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()
            timePicker.addOnNegativeButtonClickListener{
                val minute = if (timePicker.minute in 0..9) "0${timePicker.minute}" else timePicker.minute
                val hour = if (timePicker.hour in 0..9) "0${timePicker.hour}" else timePicker.hour
                binding.talHour.text = "$hour:$minute"

            }
            timePicker.show(supportFragmentManager,null)

        }
        binding.btnCancel.setOnClickListener{
            finish()
        }
        binding.btnNewTask.setOnClickListener {
            val task = Task(
                title = binding.tilTitle.text ,
                date= binding.tilDate.text,
                hour= binding.talHour.text,
                id = intent.getIntExtra(TASK_ID,0)
            )
            TaskDataSource.insertTask(task)
            setResult(Activity.RESULT_OK)
            finish()
        }
    }
    companion object{
        const val TASK_ID ="task_id"
    }
}