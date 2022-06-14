package com.example.samplehiltretrofit.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import com.example.samplehiltretrofit.data.model.Character
import com.example.samplehiltretrofit.data.model.CharacterList
import com.example.samplehiltretrofit.data.repository.CharacterRepository
import com.example.samplehiltretrofit.util.ResultResource
import com.example.samplehiltretrofit.viewmodel.CharacterListViewModel.Event.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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

    var characterLisResponse: List<Character> by mutableStateOf(listOf())
    private var characters_mutable=MutableLiveData<List<Character>>()

    val characters:LiveData<List<Character>> =characters_mutable

    sealed class Event {
        data class Success(val characterList: CharacterList) : Event()
        data class Loading(val loading: Boolean) : Event()
        data class Failed(val error: String?) : Event()
    }

    private val eventChanel = Channel<Event>()
    val eventFlow = eventChanel.receiveAsFlow()

    // val response: LiveData<ResultResource<CharacterList>> = _response
    fun fetchCharList() = viewModelScope.launch {
        characterRepository.getCharactersList().collect { values ->
            //   _response.value = values
            values.data?.let {
                when (values) {
                    is ResultResource.SUCCESS -> {
                        with(eventChanel) {
                            send(Event.Loading(false))
                            send(Success(values.data))
                        }
                    }
                    is ResultResource.LOADING -> {
                        eventChanel.send(Event.Loading(true))
                    }
                    is ResultResource.ERROR -> {
                        with(eventChanel) {
                            send(Event.Loading(false))
                            send(Event.Failed(values.message))
                        }

                    }
                }

            }
            //eventChanel.send(values.data?.let { Event.Success(it) })
        }

    }
    fun getCharacterList()= liveData(Dispatchers.IO) {
        emit(characterRepository.getCharactersLiveData())
    }



    /*class MainViewModel : ViewModel() {
    var movieListResponse:List<Movie> by mutableStateOf(listOf())
    var errorMessage: String by mutableStateOf("")
    fun getMovieList() {
        viewModelScope.launch {
            val apiService = ApiService.getInstance()
            try {
                val movieList = apiService.getMovies()
                movieListResponse = movieList
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }
}*/
}