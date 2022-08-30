package com.danibelmonte.marvelcompose.ui.characters

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.danibelmonte.marvelcompose.data.entities.Character
import com.danibelmonte.marvelcompose.ui.common.MarvelItemDetailScreen
import com.danibelmonte.marvelcompose.ui.common.MarvelScreenItems

@Composable
fun CharactersScreen(characterViewModel: CharactersViewModel = viewModel(), onClick: (Character) -> Unit){
    MarvelScreenItems(
        loading = characterViewModel.state.loading,
        items = characterViewModel.state.items,
        onClick = onClick
    )
}

@Composable
fun CharacterDetailScreen(viewModel: CharacterDetailsViewModel = viewModel(), onBackAction: () -> Unit) {
    MarvelItemDetailScreen(
        loading = viewModel.state.loading,
        marvelItem = viewModel.state.character,
        onBackAction
    )
}





