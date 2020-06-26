package com.musclegamez.fitness_app.ui.tasks.model

import com.google.gson.annotations.SerializedName

data class TaskResponse(
        val status:Boolean,
        val msg:String,
        @SerializedName("data")
        val taskData:ArrayList<TaskData>
) {
}