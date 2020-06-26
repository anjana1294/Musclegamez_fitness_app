package com.musclegamez.fitness_app.ui.settings.model.profile

import com.google.gson.annotations.SerializedName

data class ProfileResponse(val status: Boolean,
                           val msg: String,
                           @SerializedName("data") val profileData: ProfileData) {
}