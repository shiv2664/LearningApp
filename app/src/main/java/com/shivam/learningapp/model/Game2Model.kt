package com.shivam.learningapp.model

import com.google.gson.annotations.SerializedName

data class Game2Model(@SerializedName("area") var area : String,
                      @SerializedName("level") var level : Int,
                      @SerializedName("quizlist") var quizlist : List<QuizData>,
                      @SerializedName("version") var version : String,
                      @SerializedName("author") var author : String,
                      @SerializedName("email") var email : String,
                      @SerializedName("result_code") var resultCode : String,
                      @SerializedName("result_msg") var resultMsg : String ){}

