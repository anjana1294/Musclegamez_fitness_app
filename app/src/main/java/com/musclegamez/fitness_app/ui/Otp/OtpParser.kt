package com.musclegamez.fitness_app.ui.Otp

import com.app.musclegamez.fitness_app.Otp.model.OTPResponse
import retrofit2.Response
import java.io.IOException

internal object OtpParser {

    @Throws(IOException::class, NullPointerException::class)
    fun parse(response: Response<OTPResponse>): String {
        if (response.isSuccessful) {
            val body = response.body()
            return if (body!!.status) {
                body.token
            } else {
                throw RuntimeException(body.msg)
            }
        } else {
            throw RuntimeException(response.message())
        }
    }
}