package com.musclegamez.fitness_app.network

import android.text.TextUtils
import android.util.Log
import com.musclegamez.fitness_app.ui.data.pref.SharedPreferencesHelper
import com.musclegamez.fitness_app.util.Constants
import okhttp3.Interceptor
import okhttp3.Response

/**
 * CCreated by root on 11/05/20.
 */
class TokenInterceptor constructor(private val tokens: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val original = chain.request()
        Log.e("Request", original.toString());
        if (TextUtils.isEmpty(tokens)) {
            return chain.proceed(original)
        }

        var token = SharedPreferencesHelper.getPrefValue(Constants.TOKEN,"")
//        var token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ2ZXJpZmllZCI6dHJ1ZSwib3RwRm9yZ2V0UGFzc3dvcmQiOiIwIiwiX2lkIjoiNWRlOWVhMDAyMzYxMTMwZjY0N2EwZjE3IiwibmFtZSI6Im5lZXJhaiBrdW1hciIsImVtYWlsIjoibmVlcmFqLmt1bWFyQGFwcHNpbWl0eS5jb20iLCJwYXNzd29yZCI6ImJhODFkN2M3YzM5MDY5ODQ0YTI5MTM1ZTkyNjM0MTE5YTRlZThkNmE3MWEzMmRjYWUyOTliNWU5YTE4ODZhMmQ2YTk5ZGQ5N2Q1YWM5ZmY3ZjMwMTkzZTI1MDUxZjI1NDc1ODNhYjUyMzIxMTY3YmU0NTBkMWJjNmVkYTU1ZTA5ZWFmYWE0NGMzYWVkMDE4YjRjNDIwYzJmZjRhNmVmYjg5OWY2ZmE4Mjg3YzYzNjFmNWVmY2E3ZjE2ZmViY2Y5NWRkZGViMGNlNjY1OCIsInBob25lIjoiOTg3NjU0MzIxMCIsInJvbGUiOiJjdXN0b21lciIsImNyZWF0ZWRBdCI6MTU3NTYxMDgyNzIxMCwidXBkYXRlZEF0IjoxNTc1NjEwODI3MjEwLCJfX3YiOjAsImlhdCI6MTU3NTYxMDkyNn0.A6cBxxSbAcodTNnQWbzsE_kxTrNSHbz885PIfgFWNpI"
        val originalHttpUrl = original.url()
        val requestBuilder = original.newBuilder()
                .addHeader("token", token)
                .url(originalHttpUrl)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}