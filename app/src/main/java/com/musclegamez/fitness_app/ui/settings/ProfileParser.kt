package com.musclegamez.fitness_app.ui.settings

import com.musclegamez.fitness_app.ui.settings.model.profile.ProfileData
import com.musclegamez.fitness_app.ui.settings.model.profile.ProfileResponse
import retrofit2.Response
import java.io.IOException

internal object ProfileParser {
    @Throws(IOException::class, NullPointerException::class)
    fun parse(response: Response<ProfileResponse>): ProfileData {
        if (response.isSuccessful) {
            val body = response.body()
            return if (body!!.status) {
                if (body.msg != null) {
                    body.profileData
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