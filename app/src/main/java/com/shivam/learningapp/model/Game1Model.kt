package com.shivam.learningapp.model

import com.google.gson.annotations.SerializedName

data class Game1Model (

    @SerializedName("word") var word : String,
    @SerializedName("examples") var examples : List<String>

)