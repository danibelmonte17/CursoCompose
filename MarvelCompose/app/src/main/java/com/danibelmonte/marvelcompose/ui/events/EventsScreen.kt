package com.danibelmonte.marvelcompose.ui.events

import androidx.compose.runtime.*
import com.danibelmonte.marvelcompose.ui.common.MarvelItemDetailScreen
import com.danibelmonte.marvelcompose.ui.common.MarvelScreenItems
import com.danibelmonte.marvelcompose.data.entities.Event
import com.danibelmonte.marvelcompose.data.repositories.EventsRepository

@Composable
fun EventsScreen(onClick: (Event) -> Unit){
    var eventsState by remember { mutableStateOf(emptyList<Event>()) }
    LaunchedEffect(Unit){
        eventsState = EventsRepository.get()
    }
    MarvelScreenItems(
        true,
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
        MarvelItemDetailScreen(marvelItem = it, onBackAction = onBackAction)
    }
}