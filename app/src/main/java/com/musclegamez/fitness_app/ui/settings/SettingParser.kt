package com.musclegamez.fitness_app.ui.settings

import com.musclegamez.fitness_app.ui.settings.model.setting.SettingData
import com.musclegamez.fitness_app.ui.settings.model.setting.SettingResponse
import retrofit2.Response
import java.io.IOException

internal object SettingParser {
    @Throws(IOException::class, NullPointerException::class)
    fun parse(response: Response<SettingResponse>): SettingData {
        if (response.isSuccessful) {
            val body = response.body()
            return if (body!!.status) {
                if (body.msg != null) {
                    body.settingData
                } else {
                    throw RuntimeException("Response payload is empty!")
                }
            } else {
                throw RuntimeException(body.msg)
            }
        } else {
            throw RuntimeException(response.message())
        }
    }
}