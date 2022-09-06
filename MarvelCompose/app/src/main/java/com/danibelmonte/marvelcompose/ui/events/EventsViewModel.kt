package com.danibelmonte.marvelcompose.ui.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.right
import com.danibelmonte.marvelcompose.data.entities.Event
import com.danibelmonte.marvelcompose.data.entities.Result
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
        val items: Result<List<Event>> = emptyList<Event>().right()
    )

}