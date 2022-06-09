package com.example.samplehiltretrofit.data.model

import com.google.gson.annotations.SerializedName

data class Character(

    val id: Int,

    val name: String,

    val status: String,
    val species: String,
    val type: String?,
    val gender: String,
    @SerializedName("origin")
    val origin: Origin,
    @SerializedName("location")
    val characterLocation: CharacterLocation,
    @SerializedName("image")
    val charImage: String,
    @SerializedName("episode")
    val episodeList: List<String>,
    val url: String,
    val created: String)