package com.example.taskbuddy

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//Marks this class a room database
@Database(entities = [TaskEntity::class], version = 1)


abstract class TaskDataBase :RoomDatabase(){

    //Gives access to the function TaskDao
    abstract fun taskDao(): TaskDao

    //Used to create the Instance without any manual Intervention
    companion object{
        //Annotation which means that forces all threads to read the recent value
        @Volatile

        private var INSTANCE: TaskDataBase? = null

        fun getInstance(context: Context): TaskDataBase =
            INSTANCE?: synchronized(this){

                INSTANCE?: Room.databaseBuilder(
                    context.applicationContext,
                    TaskDataBase::class.java,
                    "task_db"
                ).build().also { INSTANCE = it }
            }
    }
}