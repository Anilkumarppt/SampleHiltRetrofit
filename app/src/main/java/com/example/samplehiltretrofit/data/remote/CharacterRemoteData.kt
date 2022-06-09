package com.example.samplehiltretrofit.data.remote

import com.example.samplehiltretrofit.data.api.CharacterService
import com.example.samplehiltretrofit.data.model.BaseDataSource
import javax.inject.Inject

class CharacterRemoteData @Inject constructor(private val characterService: CharacterService) {
    suspend fun getCharacters() = characterService.getAllCharacters()
}

