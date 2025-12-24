package com.example.taskbuddy

class TaskItem {
    var taskName: String? = null
    var taskDescription: String? = null
    var dueDate: String? = null
    var priority: String? = null
    var isCompleted: Boolean = false
    constructor(taskName:String, taskDescription: String, dueDate:String, priority:String, isCompleted:Boolean){
        this.taskName = taskName
        this.taskDescription = taskDescription
        this.dueDate = dueDate
        this.priority = priority
        this.isCompleted = isCompleted
    }
}