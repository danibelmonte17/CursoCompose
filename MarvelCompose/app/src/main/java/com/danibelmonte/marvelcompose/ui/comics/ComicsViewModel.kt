package com.danibelmonte.marvelcompose.ui.comics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.right
import com.danibelmonte.marvelcompose.data.entities.Comic
import com.danibelmonte.marvelcompose.data.entities.Result
import com.danibelmonte.marvelcompose.data.repositories.ComicsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ComicsViewModel : ViewModel() {

    val state =
        Comic.Format.values().associateWith { MutableStateFlow(UiState()) }

    data class UiState(
        val loading: Boolean = false,
        val items: Result<List<Comic>> = emptyList<Comic>().right()
    )

    fun formatRequested(format: Comic.Format) {
        val uiState = state.getValue(format)
        if (uiState.value.items.isNotEmpty()) return

        viewModelScope.launch {
            uiState.value = UiState(loading = true)
            uiState.value = UiState(items = ComicsRepository.get(format))
        }
    }


}