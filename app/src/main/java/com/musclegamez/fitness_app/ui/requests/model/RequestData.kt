package com.musclegamez.fitness_app.ui.requests.model

import com.google.gson.annotations.SerializedName

data class RequestData(
        val status:Boolean,
        val msg:String,
//        @SerializedName("data")
//        val requestData:RequestResponse

        @SerializedName("data")
       val requestData:ArrayList<RequestResponse>
) {
}