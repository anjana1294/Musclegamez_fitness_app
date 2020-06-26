package com.musclegamez.fitness_app.ui.login

import com.musclegamez.fitness_app.ui.login.model.LoginResponse
import retrofit2.Response

internal object LoginParser {
    fun parse(response: Response<LoginResponse>): String {
    if (response.isSuccessful) {
        val body = response.body()
        return if (body!!.status) {
            if (body.token != null) {
                body.token
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