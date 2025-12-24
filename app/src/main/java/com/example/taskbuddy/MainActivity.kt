package com.example.taskbuddy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        if(savedInstanceState==null){
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainer, TaskListFragment())
                commit()
            }
        }
        //Uncomment this when you want to delete the table in the database
//        clearDatabase()
    }
    private fun clearDatabase(){
        val db = TaskDataBase.getInstance(this)
        lifecycleScope.launch {
            db.taskDao().deleteAlltasks()
        }
    }
}