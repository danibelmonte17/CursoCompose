package com.danibelmonte.marvelcompose.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danibelmonte.marvelcompose.data.entities.Character
import com.danibelmonte.marvelcompose.data.repositories.CharactersRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CharactersViewModel : ViewModel() {

     val state = MutableStateFlow(UiState())

    init {
        viewModelScope.launch {
            state.value = UiState(loading = true)
            state.value = UiState(items = CharactersRepository.get())
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val items: List<Character> = emptyList()
    )

}