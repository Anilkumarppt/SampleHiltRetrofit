package com.example.samplehiltretrofit.data.model

import com.google.gson.annotations.SerializedName

data class CharacterLocation(
    val name: String,
    @SerializedName("url")
    val locationUrl: String
)
