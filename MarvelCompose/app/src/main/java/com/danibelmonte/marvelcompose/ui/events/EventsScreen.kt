package com.danibelmonte.marvelcompose.ui.events

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.danibelmonte.marvelcompose.data.entities.Event
import com.danibelmonte.marvelcompose.ui.common.MarvelItemDetailScreen
import com.danibelmonte.marvelcompose.ui.common.MarvelScreenItems

@Composable
fun EventsScreen(viewModel: EventsViewModel = viewModel(), onClick: (Event) -> Unit){
    val state by viewModel.state.collectAsState()
    MarvelScreenItems(
        loading = state.loading,
        items = state.items,
        onClick = onClick
    )
}

@Composable
fun EventsDetailsScreen(viewModel: EventsDetailsViewModel = viewModel(),  onBackAction: () -> Unit){
    val state by viewModel.state.collectAsState()
    MarvelItemDetailScreen(
        loading = state.loading,
        marvelItem = state.event,
        onBackAction = onBackAction
    )
}