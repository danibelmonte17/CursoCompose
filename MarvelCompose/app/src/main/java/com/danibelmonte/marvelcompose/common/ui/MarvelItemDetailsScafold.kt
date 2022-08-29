package com.danibelmonte.marvelcompose.ui.screens.characterdetail

import android.content.Context
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ShareCompat
import com.danibelmonte.marvelcompose.common.ui.AppBarIcons
import com.danibelmonte.marvelcompose.data.entities.MarvelItem

@Composable
fun MarvelItemDetailsScafold(
    marvelItem: MarvelItem,
    onBackAction: () -> Unit,
    content: @Composable (PaddingValues) -> Unit
){

    val context = LocalContext.current

    Scaffold(
        /*topBar = {
            TopAppBar(
                title = { Text(marvelItem.title) },
                navigationIcon = {
                    IconButton(
                        onClick = onBackAction
                    ) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    AppOverflowMenuDetails(urls = marvelItem.urls)
                }
            )
        },*/
        floatingActionButton = {
            if(marvelItem.urls.isNotEmpty()){
                FloatingActionButton(
                    backgroundColor = MaterialTheme.colors.secondary,
                    onClick = { sharedMarvelItem(context, marvelItem) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share",
                        tint = Color.White
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        bottomBar = {
            BottomAppBar(
                cutoutShape = CircleShape
            ) {
                AppBarIcons(imageVector = Icons.Default.Menu) {}
                Spacer(modifier = Modifier.weight(1f))
                AppBarIcons(imageVector = Icons.Default.Favorite) {}
            }
        },
        content = content
    )
}

fun sharedMarvelItem(context: Context, marvelItem: MarvelItem) {
    ShareCompat
        .IntentBuilder(context)
        .setType("text/plain")
        .setSubject(marvelItem.title)
        .setText(marvelItem.urls.first().destination)
        .intent
        .also {
            context.startActivity(it)
        }
}
