package com.musclegamez.fitness_app.ui.home.model

import com.google.gson.annotations.SerializedName


data class CreateTask (val status:Boolean,
                       val msg:String,
                       @SerializedName("data")
                       val taskData:ArrayList<MyCreateTaskResponse>){

}