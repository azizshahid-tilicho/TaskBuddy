package com.example.taskbuddy

class TaskItem {
    val taskId: Long
    var taskName: String? = null
    var taskDescription: String? = null
    var dueDate: Long? = null
    var priority: String? = null
    var isCompleted: Boolean = false
    constructor(taskId:Long, taskName:String, taskDescription: String, dueDate: Long, priority:String, isCompleted:Boolean){
        this.taskId = taskId
        this.taskName = taskName
        this.taskDescription = taskDescription
        this.dueDate = dueDate
        this.priority = priority
        this.isCompleted = isCompleted
    }
}