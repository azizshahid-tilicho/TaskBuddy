package com.example.taskbuddy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.taskbuddy.databinding.FragmentTaskDetailBinding
import kotlinx.coroutines.launch


class TaskDetailFragment : Fragment() {

    private lateinit var binding: FragmentTaskDetailBinding
    private var taskId: Long = 0L
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentTaskDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        taskId = arguments?.getLong(ARG_ID)?:0L
        val title = arguments?.getString(ARG_TITLE).orEmpty()
        val description = arguments?.getString(ARG_DESCRIPTION).orEmpty()
        val dueDate = arguments?.getString((ARG_DUE_DATE)).orEmpty()
        val priority= arguments?.getString(ARG_PRIORITY).orEmpty()
        val completed = arguments?.getBoolean(ARG_ISCOMPLETED)?:false


        binding.tvNameDetail.text = title
        binding.tvDescriptionDetail.text = description
        binding.tvDueDateDetail.text = dueDate
        binding.tvPriorityDetail.text = priority
        binding.checkBoxDetail.isChecked = completed

        binding.btnBack.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, TaskListFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.btnMark.setOnClickListener{
            val newCompleted = binding.checkBoxDetail.isChecked
            updateCompletedInDb(newCompleted)
            parentFragmentManager.popBackStack()
        }

        when(priority.uppercase()){
            "LOW"   -> binding.tvPriorityDetail.setBackgroundColor(
                ContextCompat.getColor(requireContext(), R.color.color_priority_low)
            )
            "HIGH" -> binding.tvPriorityDetail.setBackgroundColor(
                ContextCompat.getColor(requireContext(), R.color.color_priority_high)
            )
            "MEDIUM" -> binding.tvPriorityDetail.setBackgroundColor(
                ContextCompat.getColor(requireContext(),R.color.color_priority_medium)
            )
        }
    }
    private fun updateCompletedInDb(completed: Boolean){
            val db= TaskDataBase.getInstance(requireContext())
            viewLifecycleOwner.lifecycleScope.launch {
                db.taskDao().updatedCompleted(taskId, completed)
            }
    }

    companion object {
        private const val ARG_ID = "arg_id"
        private const val ARG_TITLE = "arg_title"
        private const val ARG_DESCRIPTION = "arg_description"
        private const val ARG_DUE_DATE = "arg_due_date"
        private const val ARG_PRIORITY = "arg_priority"
        private const val ARG_ISCOMPLETED = "arg_iscompleted"

        @JvmStatic
        fun newInstance(task: TaskItem): TaskDetailFragment {
            return TaskDetailFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_ID, task.taskId)
                    putString(ARG_TITLE, task.taskName)
                    putString(ARG_DESCRIPTION, task.taskDescription)
                    task.dueDate?.let { putLong(ARG_DUE_DATE, it) }
                    putString(ARG_PRIORITY, task.priority)
                    putBoolean(ARG_ISCOMPLETED, task.isCompleted)
                }
            }
        }
    }
}