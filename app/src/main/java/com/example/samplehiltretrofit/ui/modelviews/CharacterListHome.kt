package com.example.samplehiltretrofit.ui.modelviews

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.samplehiltretrofit.data.model.Character
import com.example.samplehiltretrofit.data.repository.CharacterRepository
import javax.inject.Inject

@Composable
fun RecyclerList(list: List<Character>){
    val context= LocalContext.current
    val list= remember {list }
    LazyColumn(contentPadding = PaddingValues(all = 12.dp)){
        items(
            items = list,
            itemContent = {
                UserCard(character = it)
            }
        )
    }
}
