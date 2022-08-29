package com.danibelmonte.marvelcompose.ui.screens

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import com.danibelmonte.marvelcompose.common.ui.MarvelItemDetailScreen
import com.danibelmonte.marvelcompose.common.ui.MarvelScreenItems
import com.danibelmonte.marvelcompose.data.entities.Event
import com.danibelmonte.marvelcompose.data.repositories.EventsRepository

@Composable
fun EventsScreen(onClick: (Event) -> Unit){
    var eventsState by rememberSaveable { mutableStateOf(emptyList<Event>()) }
    LaunchedEffect(Unit){
        eventsState = EventsRepository.get()
    }
    MarvelScreenItems(
        items = eventsState,
        onClick = onClick
    )
}

@Composable
fun EventsDetailsScreen(itemId: Int, onBackAction: () -> Unit){
    var eventState by remember { mutableStateOf<Event?>(null) }
    LaunchedEffect(Unit){
        eventState = EventsRepository.find(itemId)
    }
    eventState?.let {
        MarvelItemDetailScreen(marvelItem = it, onBackAction)
    }
}