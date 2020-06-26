package com.musclegamez.fitness_app.ui.home.location.model

import java.io.Serializable

data class Location(val address: String, val lat: Double, val long: Double): Serializable {
}