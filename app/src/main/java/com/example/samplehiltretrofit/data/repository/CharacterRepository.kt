package com.example.samplehiltretrofit.data.repository

import com.example.samplehiltretrofit.data.model.BaseDataSource
import com.example.samplehiltretrofit.data.model.Character
import com.example.samplehiltretrofit.data.model.CharacterList
import com.example.samplehiltretrofit.data.remote.CharacterRemoteData
import com.example.samplehiltretrofit.util.ResultResource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

import javax.inject.Inject

@ActivityRetainedScoped
class CharacterRepository @Inject constructor(val characterRemoteData: CharacterRemoteData):
    BaseDataSource() {


    suspend fun  getCharactersList(): Flow<ResultResource<CharacterList>> {
        return flow {
            emit(safeApiCall { characterRemoteData.getCharacters()})
        }.flowOn(Dispatchers.IO)
    }
    suspend fun getCharactersLiveData()=characterRemoteData.getCharacters()
}