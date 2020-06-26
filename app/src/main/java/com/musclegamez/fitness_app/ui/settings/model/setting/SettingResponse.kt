package com.musclegamez.fitness_app.ui.settings.model.setting

import com.google.gson.annotations.SerializedName

data class SettingResponse(val status: Boolean,
                      val msg: String,
                      @SerializedName("data") val settingData: SettingData) {
}