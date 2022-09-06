package com.danibelmonte.marvelcompose.ui.characters

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.danibelmonte.marvelcompose.data.entities.Character
import com.danibelmonte.marvelcompose.ui.common.MarvelItemDetailScreen
import com.danibelmonte.marvelcompose.ui.common.MarvelScreenItems

@Composable
fun CharactersScreen(characterViewModel: CharactersViewModel = viewModel(), onClick: (Character) -> Unit){
    val state by characterViewModel.state.collectAsState()
    MarvelScreenItems(
        loading = state.loading,
        items = state.items,
        onClick = onClick
    )
}

@Composable
fun CharacterDetailScreen(viewModel: CharacterDetailsViewModel = viewModel(), onBackAction: () -> Unit) {
    val state by viewModel.state.collectAsState()
    MarvelItemDetailScreen(
        loading = state.loading,
        marvelItem = state.character,
        onBackAction
    )
}





