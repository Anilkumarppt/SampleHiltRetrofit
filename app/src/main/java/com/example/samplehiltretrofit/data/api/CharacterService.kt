package com.example.samplehiltretrofit.data.api

import com.example.samplehiltretrofit.data.model.Character
import com.example.samplehiltretrofit.data.model.CharacterList
import com.example.samplehiltretrofit.util.ResultResource
import retrofit2.Response

import retrofit2.http.GET

interface CharacterService {

    @GET("character")
    suspend fun getAllCharacters():Response<CharacterList>

}