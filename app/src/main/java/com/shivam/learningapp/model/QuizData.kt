package com.shivam.learningapp.model

import com.google.gson.annotations.SerializedName

data class QuizData(  @SerializedName("quiz") var quiz : List<String>,
                      @SerializedName("option") var option : List<String>,
                      @SerializedName("correct") var correct : Int){}
