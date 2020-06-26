package com.musclegamez.fitness_app.ui.settings

import com.musclegamez.fitness_app.ui.settings.model.aboutUs.AboutReponse
import retrofit2.Response
import java.io.IOException


internal object AboutParser {
    @Throws(IOException::class, NullPointerException::class)
    fun parse(response: Response<AboutReponse>): String {
        if (response.isSuccessful) {
            val body = response.body()
            return if (body!!.status) {
                if (body.msg != null) {
                    body.msg
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