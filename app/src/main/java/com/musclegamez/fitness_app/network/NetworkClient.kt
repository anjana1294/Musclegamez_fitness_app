package com.musclegamez.fitness_app.network

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * CCreated by root on 11/05/20.
 */
object NetworkClient {
    private val apiEndpoint = "https://gymfitnessapp.herokuapp.com/"

    private val READ_TIME_OUT = 30
    private val CONNECT_TIMEOUT = 10

    fun create(token: String): ApiServices {

        val okHttpBuilder =
                OkHttpClient.Builder()
        okHttpBuilder.connectTimeout(CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
        okHttpBuilder.readTimeout(READ_TIME_OUT.toLong(), TimeUnit.SECONDS)
        okHttpBuilder.addInterceptor(TokenInterceptor(token))
        okHttpBuilder.addInterceptor(
                HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> Log.d("OkHttp", message) })
                        .setLevel(HttpLoggingInterceptor.Level.BODY)
        )

        val okHttpClient = okHttpBuilder.build()

//        val okHttpBuilder = OkHttpClient.Builder()
//        okHttpBuilder.connectTimeout(CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
//        okHttpBuilder.readTimeout(READ_TIME_OUT.toLong(), TimeUnit.SECONDS)
//        okHttpBuilder.addInterceptor(
//                HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> Log.d("OkHttp", message) })
//                        .setLevel(HttpLoggingInterceptor.Level.BODY)
//        )
//        okHttpBuilder.addInterceptor(ChuckInterceptor(context))
//        okHttpBuilder.cache(null)
//        val okHttpClient = okHttpBuilder.build()

        val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(apiEndpoint)
                .build()

        return retrofit.create(ApiServices::class.java);
    }
}