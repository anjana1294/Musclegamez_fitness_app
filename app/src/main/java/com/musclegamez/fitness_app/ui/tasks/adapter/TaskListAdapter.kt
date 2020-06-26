package com.musclegamez.fitness_app.ui.tasks.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.musclegamez.fitness_app.R
import kotlinx.android.synthetic.main.item_layout_tasklist.view.*

class TaskListAdapter(
        //val taskList: ArrayList<TaskData>,
        val items : ArrayList<String>,
        val context: Context) : Adapter<TaskListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_layout_tasklist, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.exerciseTitle?.text = items.get(position)
//        holder.startTime?.text = taskList.get(position).start_date
//        holder.endTime?.text = taskList.get(position).end_time

//        if (taskList.get(position).exerciseName == "running") {
//            holder.exerciseImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.running));
//        } else if (taskList.get(position).exerciseName == "pushup") {
//            holder.exerciseImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.pushup));
//        } else {
//            holder.exerciseImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.pushup));
//        }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val exerciseTitle = view.tv_exercise_title
        val startTime = view.tv_start_time
        val endTime = view.tv_end_time
        val exerciseImage = view.iv_exercise
    }

}