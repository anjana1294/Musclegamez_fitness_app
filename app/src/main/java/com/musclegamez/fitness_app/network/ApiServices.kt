package com.musclegamez.fitness_app.network

import com.app.musclegamez.fitness_app.Otp.model.OTPResponse
import com.musclegamez.fitness_app.ui.Otp.model.OTPRequest
import com.musclegamez.fitness_app.ui.login.model.LoginRequest
import com.musclegamez.fitness_app.ui.login.model.LoginResponse
import com.musclegamez.fitness_app.ui.settings.model.aboutUs.AboutReponse
import com.musclegamez.fitness_app.ui.settings.model.aboutUs.AboutRequest
import com.musclegamez.fitness_app.ui.settings.model.chatSupport.ChatRequest
import com.musclegamez.fitness_app.ui.settings.model.chatSupport.ChatResponse
import com.musclegamez.fitness_app.ui.settings.model.profile.EditProfileRequest
import com.musclegamez.fitness_app.ui.settings.model.profile.ProfileResponse
import com.musclegamez.fitness_app.ui.settings.model.setting.EditSettingRequest
import com.musclegamez.fitness_app.ui.settings.model.setting.SettingResponse
import com.musclegamez.fitness_app.ui.signup.model.SignupRequest
import com.musclegamez.fitness_app.ui.signup.model.SignupResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


/**
 * Created by root on 11/05/20.
 */
interface ApiServices {

    //    Login Screen APIS
    @Headers("content-type: application/json")
    @POST("/users/login")
    fun login(@Body request: LoginRequest): Observable<Response<LoginResponse>>


    //    SignUp Screen APIS
    @Headers("content-type: application/json")
    @POST("/users/register")
    fun signup(@Body request: SignupRequest): Observable<Response<SignupResponse>>


    //   OTP Screen APIS
    @Headers("content-type: application/json")
    @POST("/users/confirmation")
    fun verifyOTP(@Body request: OTPRequest): Observable<Response<OTPResponse>>


    //   Profile Screen APIS--GET
    @Headers("content-type: application/json")
    @POST("/users/profile")
    fun getProfile(): Observable<Response<ProfileResponse>>

    //   Profile Screen APIS--EDIT
    @Headers("content-type: application/json")
    @POST("/users/editProfile")
    fun updateProfile(@Body request: EditProfileRequest): Observable<Response<ProfileResponse>>


    //  Setting Screen APIS--SETTING-GET
    @Headers("content-type: application/json")
    @POST("/users/profile")
    fun getProfileImage(): Observable<Response<SettingResponse>>



    //   Setting Screen APIS--EDIT
    @Headers("content-type: application/json")
    @POST("/users/editProfileImage")
    fun updateProfileImage(@Body request: EditSettingRequest): Observable<Response<SettingResponse>>


    //   AboutUs  Screen APIS
    @Headers("content-type: application/json")
    @POST("/users/feedback")
    fun onAbout(@Body request: AboutRequest): Observable<Response<AboutReponse>>


    //   Chat Support Screen APIS-
    @Headers("content-type: application/json")
    @POST("/users/chatSupport")
    fun onChatSupport(@Body request: ChatRequest): Observable<Response<ChatResponse>>
}
