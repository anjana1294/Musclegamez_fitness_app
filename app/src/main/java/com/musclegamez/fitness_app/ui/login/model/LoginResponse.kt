package com.musclegamez.fitness_app.ui.login.model



data class LoginResponse(val status: Boolean,
                         val msg: String,
                         val token: String) {
}