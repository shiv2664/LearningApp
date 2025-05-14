package com.shivam.learningapp.api

import com.shivam.learningapp.model.Game2Model
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MyGame2WebService {

    @GET("type1/")
    fun getQuestions(@Header("x-rapidapi-key") header1 :String?,
                     @Header("x-rapidapi-host") header2:String?,
                     @Query("level") level :Int,
                     @Query("area") area :String?): Call<Game2Model?>

    companion object {

        private val loggingInterceptor  = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient= OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        private const val BASE_URL = "https://twinword-word-association-quiz.p.rapidapi.com/"
        val retrofit: Retrofit? = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
}

//        val gson =GsonBuilder().serializeNulls().create()