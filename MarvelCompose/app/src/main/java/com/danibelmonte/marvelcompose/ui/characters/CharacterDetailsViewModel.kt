package com.danibelmonte.marvelcompose.ui.characters

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danibelmonte.marvelcompose.data.entities.Character
import com.danibelmonte.marvelcompose.data.repositories.CharactersRepository
import com.danibelmonte.marvelcompose.navigation.NavArgs
import com.danibelmonte.marvelcompose.navigation.NavItem
import kotlinx.coroutines.launch

class CharacterDetailsViewModel(
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val id = savedStateHandle.get<Int>(NavArgs.ItemId.key)?:0

    var state by mutableStateOf(UiState())
        private set

    init {
        viewModelScope.launch {
            state = UiState(loading = true)
            state = UiState(character = CharactersRepository.find(id))
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val character: Character? = null
    )

}