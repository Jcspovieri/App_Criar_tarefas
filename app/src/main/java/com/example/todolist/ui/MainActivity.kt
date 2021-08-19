package com.example.todolist.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.datasource.TaskDataSource
import com.example.todolist.model.Task

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy { TaskListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvTask.adapter = adapter
        Updatelist()
        insertListeners()
    }
    private fun insertListeners(){
        binding.fab.setOnClickListener {
            startActivityForResult(Intent(this,addTaskActivity::class.java), CREATE_NEW_TASK)

        }

        adapter.listeneredit = {
            val intent = Intent(this, addTaskActivity::class.java)
            intent.putExtra(addTaskActivity.TASK_ID,it.id)
            startActivityForResult(intent, CREATE_NEW_TASK)

        }
        adapter.linesterdelete = {
            TaskDataSource.deleteTask(it)
            Updatelist()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CREATE_NEW_TASK && resultCode == Activity.RESULT_OK) Updatelist()


    }

    private fun Updatelist(){
       val list = TaskDataSource.getList()
        binding.includeEmpty.empetyState.visibility = if (list.isEmpty())
            View.VISIBLE
        else
            View.GONE

        adapter.submitList(list)
    }

    companion object {
        private const val CREATE_NEW_TASK = 1000

    }
}