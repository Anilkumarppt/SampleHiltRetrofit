package com.example.samplehiltretrofit.ui.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import com.example.samplehiltretrofit.R
import com.example.samplehiltretrofit.data.model.Character
import com.example.samplehiltretrofit.data.model.CharacterList
import com.example.samplehiltretrofit.data.remote.CharacterRemoteData
import com.example.samplehiltretrofit.ui.modelviews.RecyclerList
import com.example.samplehiltretrofit.ui.modelviews.UserCard
import com.example.samplehiltretrofit.viewmodel.CharacterListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.Response

@AndroidEntryPoint
class JetPackComposeListActivity : ComponentActivity() {
    private val characterListViewModel by viewModels<CharacterListViewModel>()

    private val TAG = "JetPackComposeListActiv"
    var charaterList = CharacterList(info = null, listOf())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //fetchData()
            Surface(color = MaterialTheme.colors.background) {

                CharacterListCompose(characterListViewModel = characterListViewModel)

            }


            //    Greeting(name = "Anil")
            //UserCard()
            /*SampleHiltRetrofitTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }*/
        }

    }

    private fun fetchData() {
        //   mBinding.pbDog.visibility = View.VISIBLE
        characterListViewModel.fetchCharList()
        observeEventData()
    }

    private fun observeEventData() {
        lifecycleScope.launchWhenCreated {
            characterListViewModel.eventFlow.collect { resultResource ->
                when (resultResource) {
                    is CharacterListViewModel.Event.Success -> {
                        //mBinding.pbDog.visibility=View.GONE
                        resultResource.characterList.let {
                            Log.d(TAG, "observeEventData: Success")
                            Log.d(TAG, "observeEventData: ")
                            charaterList = resultResource.characterList

                            //CharacterListCompose(charaterList = resultResource.characterList)

                        }
                    }
                    is CharacterListViewModel.Event.Failed -> {
                        resultResource.error.let {
                            /*Snackbar.make(
                                mBinding.root,
                                resultResource.error.toString(),
                                com.google.android.material.snackbar.Snackbar.LENGTH_LONG
                            ).show()*/
                        }
                    }
                    is CharacterListViewModel.Event.Loading -> {
                        /*                   if (resultResource.loading)
                                               mBinding.pbDog.visibility = View.VISIBLE
                                           else
                                               mBinding.pbDog.visibility = View.GONE*/
                    }
                }
            }
        }
    }


}

@Suppress("UNCHECKED_CAST")
@Composable
fun CharacterListCompose(characterListViewModel: CharacterListViewModel) {
    val list: State<Response<CharacterList>?> =
        characterListViewModel.getCharacterList().observeAsState()
    //val characterList:List<Character>=list?.value?.body()?.results?
    LazyColumn(contentPadding = PaddingValues(all = 12.dp)) {
        //val charList = list.value?.body()?.results
        list.value?.body()?.results?.size.let {
            it?.let {
                list.value?.body()?.results?.size?.let { it1 -> items(it1){
                    UserCard(character = list.value?.body()?.results?.get(it)!!)
                }
                }

            }
        }
    }
}
//RecyclerList(list = charaterList)


@Composable
fun Greeting(name: String) {
    val context = LocalContext.current
    Text(text = "Hello $name!",
        color = Color.Blue,
        fontSize = 32.sp,
        modifier = Modifier.clickable {
            Toast.makeText(context, "Click event from Textview", Toast.LENGTH_SHORT).show()
        })
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Surface(Modifier.fillMaxSize()) {
        //UserCard()
    }
}