package com.musclegamez.fitness_app.ui.requests.adapater

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.musclegamez.fitness_app.R
import kotlinx.android.synthetic.main.item_layout_request_list.view.*

class RequestListAdapter(val items: ArrayList<String>,
                         val context: Context) : RecyclerView.Adapter<RequestListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_layout_request_list, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RequestListAdapter.ViewHolder, position: Int) {
        holder.exerciseTitle?.text = items.get(position)
//        holder.startTime?.text = taskList.get(position).start_date
//        holder.endTime?.text = taskList.get(position).end_time
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val exerciseTitle = view.tv_request_title
        val startTime = view.tv_req_start_time
        val endTime = view.tv_req_end_time
        val exerciseImage = view.iv_req_exercise
        val clickDecline = view.btn_decline
        val clickAccept = view.btn_accept
    }
}