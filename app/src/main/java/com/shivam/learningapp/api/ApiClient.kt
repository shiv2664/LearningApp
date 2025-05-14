package com.shivam.learningapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
        const val BASE_URL = "https://jsonplaceholder.typicode.com/"
//    const val BASE_URL = "https://clientdev.digitianhub.online/"
//    private val client = OkHttpClient.Builder().build()
    private var retrofit: Retrofit? = null

    val apiClient: Retrofit?
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }

    fun<T> buildService(service: Class<T>): T? {
        return retrofit?.create(service)
    }

}
