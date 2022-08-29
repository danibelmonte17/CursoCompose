package com.danibelmonte.marvelcompose.ui.screens.characters

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import com.danibelmonte.marvelcompose.common.ui.MarvelItemDetailScreen
import com.danibelmonte.marvelcompose.common.ui.MarvelScreenItems
import com.danibelmonte.marvelcompose.data.entities.Character
import com.danibelmonte.marvelcompose.data.repositories.CharactersRepository

@Composable
fun CharactersScreen(onClick: (Character) -> Unit){
    var characterState by rememberSaveable{ mutableStateOf(emptyList<Character>()) }
    LaunchedEffect(Unit){
       characterState = CharactersRepository.get()
    }
    MarvelScreenItems(
        items = characterState,
        onClick = onClick
    )
}

@Composable
fun CharacterDetailScreen(itemId: Int, onBackAction: () -> Unit) {
    var characterState by remember { mutableStateOf<Character?>(null) }
    LaunchedEffect(Unit) {
        characterState = CharactersRepository.find(itemId)
    }
    characterState?.let {
        MarvelItemDetailScreen(marvelItem = it, onBackAction)
    }
}





