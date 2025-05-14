package com.shivam.learningapp.api

import com.shivam.learningapp.model.Game1Model
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface MyGame1WebService {

//    @GET("realise/")
//    fun getSentences(@Header("x-rapidapi-key") header1 :String?,
//                     @Header("x-rapidapi-host") header2:String?,
//                     @Query("object ") sentenceObject :String?,
//                     @Query("subject") sentenceSubject :String?,
//                     @Query("verb") sentenceVerb:String?): Call<Any?>

    @GET("words/{word}/examples")
    fun getSentences(@Header("x-rapidapi-key") header1 :String?,
                     @Header("x-rapidapi-host") header2:String?,
                     @Path("word") word :String?,): Call<Game1Model>


    companion object {

        private val loggingInterceptor  = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient= OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        private const val BASE_URL = "https://wordsapiv1.p.rapidapi.com/"
        val retrofit: Retrofit? = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

}