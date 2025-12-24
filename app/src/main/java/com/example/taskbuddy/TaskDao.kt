package com.example.taskbuddy

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TaskDao {

    @Insert
    suspend fun insertTask(task: TaskEntity)

    @Query("SELECT * FROM tasks")
    suspend fun getAllTasks(): List<TaskEntity>

    @Query("Delete FROM tasks")
    suspend fun deleteAlltasks()

    @Query("UPDATE tasks SET isCompleted=:completed WHERE id= :id")
    suspend fun updatedCompleted(id: Long, completed: Boolean)
}