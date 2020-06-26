package com.musclegamez.fitness_app.ui.tasks.model

import com.google.gson.annotations.SerializedName

data class TaskData(
        val _id:String,
        @SerializedName("type") val exerciseName:String,
        val title:String,
        val timer:String,
        val status:String,
        val start_time:String,
        val start_date:String,
        val senderId:String,
        val end_time:String,
        val createdById:String
       // val sender:ArrayList<SenderData>

) {
}