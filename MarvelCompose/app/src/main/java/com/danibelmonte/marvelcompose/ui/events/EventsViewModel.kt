package com.danibelmonte.marvelcompose.ui.events

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danibelmonte.marvelcompose.data.entities.Event
import com.danibelmonte.marvelcompose.data.repositories.EventsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class EventsViewModel : ViewModel() {

    var state = MutableStateFlow(UiState())

    init {
        viewModelScope.launch {
            state.value = UiState(loading = true)
            state.value = UiState(items = EventsRepository.get())
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val items: List<Event> = emptyList()
    )

}