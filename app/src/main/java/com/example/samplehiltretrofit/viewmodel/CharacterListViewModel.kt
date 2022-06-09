package com.example.samplehiltretrofit.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.samplehiltretrofit.data.model.Character
import com.example.samplehiltretrofit.data.model.CharacterList
import com.example.samplehiltretrofit.data.repository.CharacterRepository
import com.example.samplehiltretrofit.util.ResultResource
import com.example.samplehiltretrofit.viewmodel.CharacterListViewModel.Event.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.lang.Error
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
    application: Application
) : AndroidViewModel(application) {

    val _response: MutableLiveData<ResultResource<CharacterList>> = MutableLiveData()
    sealed class Event{
        data class Success(val characterList: CharacterList):Event()
        data class Loading(val loading:Boolean):Event()
        data class Failed(val error:String?):Event()
    }
    private val eventChanel= Channel<Event>()
    val eventFlow=eventChanel.receiveAsFlow()
    // val response: LiveData<ResultResource<CharacterList>> = _response
    fun fetchCharList() = viewModelScope.launch {
        characterRepository.getCharactersList().collect { values ->
         //   _response.value = values
            values.data?.let{
                when(values){
                    is ResultResource.SUCCESS ->{
                        with(eventChanel) {
                            send(Event.Loading(false))
                            send(Success(values.data))
                        }
                    }
                    is ResultResource.LOADING ->{
                        eventChanel.send(Event.Loading(true))
                    }
                    is ResultResource.ERROR ->{
                        with(eventChanel){
                            send(Event.Loading(false))
                            send(Event.Failed(values.message))
                        }

                    }
                }

            }
            //eventChanel.send(values.data?.let { Event.Success(it) })
        }

    }
}