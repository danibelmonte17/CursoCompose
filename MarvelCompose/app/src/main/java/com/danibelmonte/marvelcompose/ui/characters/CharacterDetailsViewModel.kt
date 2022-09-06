package com.danibelmonte.marvelcompose.ui.characters

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.danibelmonte.marvelcompose.data.entities.Character
import com.danibelmonte.marvelcompose.data.repositories.CharactersRepository
import com.danibelmonte.marvelcompose.navigation.NavArgs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import com.danibelmonte.marvelcompose.data.entities.Result

class CharacterDetailsViewModel(
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val id = savedStateHandle.get<Int>(NavArgs.ItemId.key)?:0

    val state = MutableStateFlow(UiState())

    init {
        viewModelScope.launch {
            state.value = UiState(loading = true)
            state.value = UiState(character = CharactersRepository.find(id))
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val character: Result<Character?> = Either.Right(null)
    )

}