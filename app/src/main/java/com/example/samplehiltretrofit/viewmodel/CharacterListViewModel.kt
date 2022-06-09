package com.example.samplehiltretrofit.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.samplehiltretrofit.data.model.Character
import com.example.samplehiltretrofit.data.model.CharacterList
import com.example.samplehiltretrofit.data.repository.CharacterRepository
import com.example.samplehiltretrofit.util.ResultResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(private val characterRepository: CharacterRepository,
                                                 application: Application
):AndroidViewModel(application) {
    val _response: MutableLiveData<ResultResource<CharacterList>> = MutableLiveData()
   // val response: LiveData<ResultResource<CharacterList>> = _response
    fun fetchCharList()=viewModelScope.launch {
        characterRepository.getCharactersList().collect { values ->
            _response.value = values
        }

    }
}