package com.example.taskbuddy

import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView

class TaskAdapter(private val context: Context, private var taskList: List<TaskItem>, private val onItemClick:(TaskItem)->Unit):
    RecyclerView.Adapter<TaskAdapter.MyViewHolder>() {

    fun updateData(newList: List<TaskItem>){
        taskList = newList
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        //Inflates the card xml layout
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view_task, parent, false)
        return MyViewHolder(view)
    }

    //Loads the existing items in the view based on the current position
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val taskItem = taskList[position]

        holder.taskName.text = taskItem.taskName
        holder.taskDescription.text = taskItem.taskDescription
        holder.dueDate.text = taskItem.dueDate.toString()
        holder.priority.text = taskItem.priority
        holder.isCompleted.isChecked = taskItem.isCompleted
        val priorityColor = when(taskItem.priority) {
            "HIGH" -> {
                R.color.color_priority_high
            }

            "LOW" -> {
                R.color.color_priority_low
            }

            "MEDIUM" -> {
                R.color.color_priority_medium
            }

            else -> {
                R.color.white
            }
        }

        holder.priority.backgroundTintList = context.getColorStateList(priorityColor)

        holder.itemView.setOnClickListener{
            onItemClick(taskItem)
        }

    }

    //get the size of the items and will be the itemList size in future
    override fun getItemCount(): Int {
        return taskList.size
    }

        //Tells what are the fields existing in the component
    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val taskName:TextView = itemView.findViewById(R.id.tvName)
        val taskDescription: TextView = itemView.findViewById(R.id.tvDescription)
        val dueDate: TextView = itemView.findViewById(R.id.tvDue)
        val priority: TextView = itemView.findViewById(R.id.tvPriority)
        val isCompleted:CheckBox = itemView.findViewById(R.id.checkBox)
    }
}