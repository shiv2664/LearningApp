package com.shivam.learningapp.model

import com.google.gson.annotations.SerializedName

data class ModelResponse (

    @SerializedName("message") val message : String,
    @SerializedName("data") val data : Data

)