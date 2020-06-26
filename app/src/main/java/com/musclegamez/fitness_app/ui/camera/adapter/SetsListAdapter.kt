package com.musclegamez.fitness_app.ui.camera.adapter

import android.content.Context
import android.text.SpannableString
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.musclegamez.fitness_app.R
import kotlinx.android.synthetic.main.item_layout_show_sets.view.*

class SetsListAdapter(val items: ArrayList<String>,
                      val context: Context) : RecyclerView.Adapter<SetsListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_layout_show_sets, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val content1 = items.get(position)
        val spannableString1 = SpannableString(content1)
        spannableString1.setSpan(StrikethroughSpan(),0,content1.length,0)

        holder.exerciseTitle?.text = spannableString1
//        holder.startTime?.text = taskList.get(position).start_date
//        holder.endTime?.text = taskList.get(position).end_time
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val exerciseTitle = view.tv_sets_exercise_title
        val startTime = view.tv_sets_start_time
        val endTime = view.tv_sets_end_time
        val exerciseImage = view.iv_sets_exercise

    }

}