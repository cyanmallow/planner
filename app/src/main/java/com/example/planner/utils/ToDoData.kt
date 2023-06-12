package com.example.planner.utils

// task here is ToDoDataJson converted to string
data class ToDoData(
    var taskId:String,
    var task:String,

)

data class ToDoDataJson(
    var desc:String = "",
    var date:String,
    var isDone:String,
    var todoCheckBox: Boolean = false)