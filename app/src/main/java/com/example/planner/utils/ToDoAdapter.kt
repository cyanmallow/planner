package com.example.planner.utils

import android.graphics.Paint
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.planner.databinding.EachTodoItemBinding
import com.google.gson.Gson

// get he list of tododata objects
class ToDoAdapter(private val list:MutableList<ToDoData>)
    : RecyclerView.Adapter<ToDoAdapter.TaskViewHolder>(){

    private var listener:TaskAdapterInterface? = null

    fun setListener(listener: TaskAdapterInterface){
        this.listener = listener
    }

    // gach cac cong viec da finished
    private fun toggleStrikeThrough(todoTask : TextView, checkBox : Boolean) {
        if (checkBox) {
            todoTask.paintFlags = todoTask.paintFlags or STRIKE_THRU_TEXT_FLAG
        } else {
            todoTask.paintFlags = todoTask.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
    }
    inner class TaskViewHolder(val binding:EachTodoItemBinding ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding =
            EachTodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)


    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        with(holder) {
            with(list[position]) {
                // turn the string to data class
                val todo = Gson().fromJson(this.task, ToDoDataJson::class.java)
                // update the card
                if(todo.desc.length > 22)
                    binding.todoTask.text = todo.desc.substring(0, 22) + "..."
                else
                    binding.todoTask.text = todo.desc

                binding.todoDate.text = todo.date

//                binding.checkBox.setOnCheckedChangeListener { compoundButton, b ->  } {
//                    binding.checkBox.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
//                }
//                binding.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
//                    binding.checkBox.paintFlags = STRIKE_THRU_TEXT_FLAG
//                }
                if(todo.isDone == "1") {
                    binding.todoIsDone.text = "Quan trọng"
                }
                else {
                    binding.todoIsDone.text = "Thường"
                }

                binding.editTask.setOnClickListener {
                    listener?.onEdit(this , position)
                }

                binding.deleteTask.setOnClickListener {
                    listener?.onDelete(this , position)
                }

                // gach dong neu da hoan thanh
                binding.todoTask.text = todo.desc
                binding.checkBox.isChecked = todo.todoCheckBox
                binding.todoDate.text = todo.date

                binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
                    toggleStrikeThrough(binding.todoTask, isChecked)
                    todo.todoCheckBox = !todo.todoCheckBox
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return  list.size
    }

    // add reference to home fragment
    interface TaskAdapterInterface{
        fun onDelete(toDoData: ToDoData, position : Int)
        fun onEdit(toDoData: ToDoData, position: Int)
    }
}