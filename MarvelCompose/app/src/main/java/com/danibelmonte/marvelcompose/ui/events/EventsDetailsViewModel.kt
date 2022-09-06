package com.danibelmonte.marvelcompose.ui.events

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.danibelmonte.marvelcompose.data.entities.Event
import com.danibelmonte.marvelcompose.data.entities.Result
import com.danibelmonte.marvelcompose.data.repositories.EventsRepository
import com.danibelmonte.marvelcompose.navigation.NavArgs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class EventsDetailsViewModel(
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val id = savedStateHandle.get<Int>(NavArgs.ItemId.key)?:0

    var state = MutableStateFlow(UiState())

    init {
        viewModelScope.launch {
            state.value = UiState(loading = true)
            state.value = UiState(event = EventsRepository.find(id))
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val event: Result<Event?> = Either.Right(null)
    )

}