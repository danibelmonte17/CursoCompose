package com.danibelmonte.marvelcompose.ui.characters

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danibelmonte.marvelcompose.data.entities.Character
import com.danibelmonte.marvelcompose.data.repositories.CharactersRepository
import kotlinx.coroutines.launch

class CharactersViewModel : ViewModel() {

     var state by mutableStateOf(UiState())
        private set

    init {
        viewModelScope.launch {
            state = UiState(loading = true)
            state = UiState(items = CharactersRepository.get())
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val items: List<Character> = emptyList()
    )

}