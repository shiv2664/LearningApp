package com.shivam.learningapp.api

import com.shivam.learningapp.model.sample
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @GET("posts")
    fun dataApi(
        @Query("userId") userId: Int): Call<List<sample?>>

    @FormUrlEncoded
    @POST("api/v1/user/activate")
    fun getOtp(
        @Field("email") email: String) : Call<Any>
//
    @FormUrlEncoded
    @POST("api/v1/user/register")
    fun register(
        @Field("name") Name: String?,
        @Field("email") Email: String?,
        @Field("password") Password: String?,
        @Field("confirmPassword") confirmPassword:String?,
        @Field("otp") otp:Int ): Call<Any>
//
//    @FormUrlEncoded
//    @POST("api/v1/user/login")
//    fun login(
//        @Field("email") email: String?,
//        @Field("password") password: String?): Call<Any>
//
//    //
////    @FormUrlEncoded
////    @POST("api/v1/user/register")
////    fun register(
////        @FieldMap registerMap: Map<String,Any>):Call<Any>
//
////    registerMap["Name"] = name
////    registerMap["Email"] = email
////    registerMap["Password"] = password
////    registerMap["otp"]= otp
//
//
////    @FormUrlEncoded
////    @POST("posts")
////    fun createPost(
////        @Field("userId") userId: Int,
////        @Field("title") title: String?,
////        @Field("body") text: String?
////    ): Call<Post?>?
////
////    @FormUrlEncoded
////    @POST("posts")
////    fun createPost(@FieldMap postMap: Map<String?, String?>?): Call<Post?>?
////
//
//
//    @GET("posts")
//    fun dataApi(
//        @Query("userId") userId: Int): List<sample?>
//
//
//
//    @GET("posts")
//    fun getPosts(
//        @Query("userId") userId:Int,
////        @Query("postId") ids: Array<Int?>?,
////        @Query("_sort") sortBy: String?,
////        @Query("_order") orderBy: String?
//    ): Call<List<sample?>?>?


}