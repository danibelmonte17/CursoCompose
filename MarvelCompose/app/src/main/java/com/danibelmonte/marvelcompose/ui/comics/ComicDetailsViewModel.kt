package com.danibelmonte.marvelcompose.ui.comics

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danibelmonte.marvelcompose.data.entities.Comic
import com.danibelmonte.marvelcompose.data.repositories.ComicsRepository
import com.danibelmonte.marvelcompose.navigation.NavArgs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ComicDetailsViewModel(
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val id = savedStateHandle.get<Int>(NavArgs.ItemId.key)?:0

    val state = MutableStateFlow(UiState())

    init {
        viewModelScope.launch {
            state.value = UiState(loading = true)
            state.value = UiState(comic = ComicsRepository.find(id))
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val comic: Comic? = null
    )


}