package com.example.taskbuddy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.lifecycleScope
import com.example.taskbuddy.databinding.FragmentAddTaskBinding
import kotlinx.coroutines.launch


class AddTaskFragment : Fragment() {
    private lateinit var binding: FragmentAddTaskBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentAddTaskBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val priorities = resources.getStringArray(R.array.Priorities)

        //Calling the Save data so that it can be stored in room Database
        binding.btnSave.setOnClickListener{
            saveTask()
        }


        //Here first parameter is requireContext() instead of this
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, priorities)
        val autoCompleteTv = binding.dropDown
        autoCompleteTv.setAdapter((arrayAdapter))

        binding.btnBack.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, TaskListFragment())
                .addToBackStack(null)
                .commit()
        }

    }
    private fun saveTask(){
        val title = binding.edtTitle.text.toString().trim()
        val description = binding.edtDescription.text.toString().trim()
        val dueDate = binding.edtDueDate2.text.toString().trim()
        val priority = binding.dropDown.text.toString().trim()
        val isCompleted = binding.checkBoxAddTask.isChecked

        if(title.isEmpty()){
            binding.edtTitle.error = "Title Required"
        }
        else if(dueDate.isEmpty()){
            binding.edtDueDate2.error = "Date is Required"
        }
        else if(priority.isEmpty()){
            binding.dropDown.error = "Priority should be chosen"
        }

        //Adding the retrived texts and all into the Task
        val task  = TaskEntity(
            title = title,
            description = description,
            dueDate = dueDate,
            priority = priority,
            isCompleted = isCompleted
        )
        //The context should be non-nullable

        val db = TaskDataBase.getInstance(requireContext())

        //only runs when the User clicks the save button

        lifecycleScope.launch {

            db.taskDao().insertTask(task)
            //Goes to backScreen or recent screen
            parentFragmentManager.popBackStack()
        }
    }

}