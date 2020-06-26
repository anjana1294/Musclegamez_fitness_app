package com.musclegamez.fitness_app.util

class Constants {
    companion object {
        const val BASE_URL="https://gymfitnessapp.herokuapp.com/"
        const val SPLASH_TIMEOUT_IN_SECONDS = 2
        const val EVENT_CONNECTIVITY_LOST = 101
        const val EVENT_CONNECTIVITY_CONNECTED = 102;
        const val LOCATION_SETTINGS_REQUEST = 3
        const val LOGOUT_DIALOG = "LogoutDialog"
        const val LoginType = "LoginType"

        //        SharedPreferencesHelper Keys
        const val TOKEN = "Token"
        const val FCMTOKEN = "fcmToken"
        const val IS_LOGGED_IN = "isLoggedIn"

        //        Fragments Tags
        const val HOME_FRAGMENT = "HomeFragment";


        //     Map
        const val MAP_FRAGMENT = "FeelThePlaceActivity";
        const val LOCATION_PERMISSION_REQUEST_CODE = 1
        const val REQUEST_CHECK_SETTINGS = 2
        const val PLACE_PICKER_REQUEST = 301

        //Google SignIn
        const val RC_SIGN_IN = 302

        //    FCM
        const val PLAY_SERVICES_RESOLUTION_REQUEST = 109




    }
}