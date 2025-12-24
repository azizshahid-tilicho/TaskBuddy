package com.example.taskbuddy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskbuddy.databinding.FragmentTaskListBinding
import kotlinx.coroutines.launch


class TaskListFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var binding: FragmentTaskListBinding
    private lateinit var adapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTaskListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.recyclerview


    //        val taskList = listOf(
    //            TaskItem(
    //                "Learn ViewModel Basics",
    //                "Watch Andriod ViewModel videos and read docs",
    //                "Due: 2025-12-08",
    //                "HIGH",
    //                false
    //
    //            ), TaskItem(
    //                "Learn ViewModel Basics",
    //                "Watch Andriod ViewModel videos and read docs",
    //                "Due: 2025-12-08",
    //                "HIGH",
    //                true
    //
    //            ), TaskItem(
    //                "Learn ViewModel Basics",
    //                "Watch Andriod ViewModel videos and read docs",
    //                "Due: 2025-12-08",
    //                "HIGH",
    //                false
    //
    //            ), TaskItem(
    //                "Learn ViewModel Basics",
    //                "Watch Andriod ViewModel videos and read docs",
    //                "Due: 2025-12-08",
    //                "HIGH",
    //                true
    //
    //            ), TaskItem(
    //                "Learn ViewModel Basics",
    //                "Watch Andriod ViewModel videos and read docs",
    //                "Due: 2025-12-08",
    //                "HIGH",
    //                false
    //
    //            ), TaskItem(
    //                "Learn ViewModel Basics",
    //                "Watch Andriod ViewModel videos and read docs",
    //                "Due: 2025-12-08",
    //                "HIGH",
    //                false
    //
    //            ), TaskItem(
    //                "Learn ViewModel Basics",
    //                "Watch Andriod ViewModel videos and read docs",
    //                "Due: 2025-12-08",
    //                "HIGH",
    //                true
    //
    //            )
    //        )
    //
    //        //Adding the sample data
    //        val taskList1 = arrayListOf<TaskItem>()
    //        taskList1.add(
    //            TaskItem(
    //                "Learn ViewModel Basics",
    //                "Watch Andriod ViewModel videos and read docs",
    //                "Due: 2025-12-08",
    //                "HIGH",
    //                true
    //
    //            )
    //        )
    //        taskList1.add(
    //            TaskItem(
    //                "Create TaskBuddy UI",
    //                "Design list, detail and add task screens",
    //                "Due: 2025-12-09",
    //                "MEDIUM",
    //                true
    //            )
    //        )
    //        taskList1.add(
    //            TaskItem(
    //                "Setup MockAPI",
    //                "Create project in MockAPI and configure endpoints.",
    //                "Due: 2025-07-07",
    //                "LOW",
    //                true
    //            )
    //        )

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        this.adapter = TaskAdapter(requireContext(), emptyList() ){
             taskItem->
                val detailFragment = TaskDetailFragment.newInstance(taskItem)
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, detailFragment)
                    .addToBackStack(null)
                    .commit()
        }

        //Connects the adapter to the recyclerview


        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())

        recyclerView.adapter = this.adapter

        binding.btnAddTask.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, AddTaskFragment())
                .addToBackStack(null)
                .commit()
        }
        loadTasksFromDb()

    }
    private fun loadTasksFromDb(){

        val db=TaskDataBase.getInstance(requireContext())

        lifecycleScope.launch{
            val entities  = db.taskDao().getAllTasks()

            val items = entities.map{
                TaskItem(
                    taskId = it.id,
                    taskName = it.title,
                    taskDescription = it.description,
                    dueDate = it.dueDate,
                    priority = it.priority,
                    isCompleted = it.isCompleted
                )
            }
            adapter.updateData(items)
        }
    }
}

