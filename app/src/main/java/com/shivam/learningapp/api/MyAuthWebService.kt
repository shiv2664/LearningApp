package com.shivam.learningapp.api

import com.shivam.learningapp.model.ModelResponse
import com.shivam.learningapp.model.sample
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface MyAuthWebService {

    @GET("posts")
    fun getPosts(
        @Query("userId") userId :String) :Call<List<sample?>?>

    @FormUrlEncoded
    @POST("api/v1/user/activate")
    fun getOtp(
        @Field("email") email: String?) : Call<ModelResponse?>

    @FormUrlEncoded
    @POST("api/v1/user/login")
    fun login(
        @Field("email") email: String?,
        @Field("password") password: String?): Call<ModelResponse?>

    @FormUrlEncoded
    @POST("api/v1/user/register")
    fun register(
        @Field("name") Name: String?,
        @Field("email") Email: String?,
        @Field("password") Password: String?,
        @Field("confirmPassword") confirmPassword:String?,
        @Field("otp") otp:Int ): Call<ModelResponse?>

    @FormUrlEncoded
    @POST("api/v1/user/reset")
    fun resetPasswordOtp(
        @Field("email") email :String?) :Call<ModelResponse?>

    @FormUrlEncoded
    @POST("api/v1/user/updatepass")
    fun resetPassword(
        @Field("email") email: String?,
        @Field("password") password: String?,
        @Field("confirmPassword") confirmPassword:String?,
        @Field("otp") otp:Int ): Call<ModelResponse?>

    @GET("api/v1/user/profile")
    fun profile(@Header("Authorization") token :String?): Call<Any?>

    @GET("api/v1/user/logout")
    fun logOutUser(@Header("Authorization") token: String?) : Call<Any?>


    companion object {

        private val loggingInterceptor  = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient= OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

//        val gson =GsonBuilder().serializeNulls().create()

        private const val BASE_URL = "https://apitest.digitianhub.online/"
        val retrofit: Retrofit? = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
}