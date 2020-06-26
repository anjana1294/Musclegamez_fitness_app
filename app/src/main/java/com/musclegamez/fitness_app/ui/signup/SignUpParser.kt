package com.musclegamez.fitness_app.ui.signup

import com.musclegamez.fitness_app.ui.signup.model.SignupResponse
import retrofit2.Response
import java.io.IOException

internal  object SignUpParser {

    @Throws(IOException::class, NullPointerException::class)
    fun parse(response: Response<SignupResponse>): String {
        if (response.isSuccessful) {
            val body = response.body()
            return if (body!!.status) {
//                if (body.msg != null) {
                body.msg
//                } else {
//                    throw RuntimeException("Response payload is empty!")
//                }
            } else {
                throw RuntimeException(body.msg)
            }
        } else {
            throw RuntimeException(response.message())
        }
    }
}