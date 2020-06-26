package com.app.musclegamez.fitness_app.Otp.model


data class OTPResponse(
        val status: Boolean,
        val msg: String,
        val token: String) {
}