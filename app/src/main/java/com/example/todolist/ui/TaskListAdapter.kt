package com.example.todolist.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.databinding.ItemTaskBinding
import com.example.todolist.model.Task

class TaskListAdapter : ListAdapter<Task,TaskListAdapter.TaskViewHoder>(DiffCallback()){

    var listeneredit: (Task) -> Unit = {}
    var linesterdelete: (Task) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHoder {
        val inflater= LayoutInflater.from(parent.context)
        val binding = ItemTaskBinding.inflate(inflater, parent, false)
        return TaskViewHoder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHoder, position: Int) {
        holder.bind(getItem(position))
    }
    inner class TaskViewHoder (private val binding: ItemTaskBinding)
        : RecyclerView.ViewHolder(binding.root){
        fun bind(item: Task) {
            binding.tvTitle.text= item.title
            binding.tvDate.text = "${item.date} ${item.hour} "
            binding.ivMore.setOnClickListener{
                showPop(item)
            }
        }
        private fun showPop(item: Task) {
            val ivMore =binding.ivMore
            val popupMenu = PopupMenu(ivMore.context,ivMore)
            popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_edit -> listeneredit(item)
                    R.id.action_delete-> linesterdelete(item)
                }
                return@setOnMenuItemClickListener true
            }
            popupMenu.show()

        }

    }


    }
class DiffCallback: DiffUtil.ItemCallback<Task>(){
    override fun areItemsTheSame(oldItem: Task, newItem: Task) = oldItem == newItem

    override fun areContentsTheSame(oldItem: Task, newItem: Task)= oldItem.id == newItem.id




}