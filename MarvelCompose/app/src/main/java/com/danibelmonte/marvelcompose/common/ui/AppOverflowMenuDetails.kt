package com.danibelmonte.marvelcompose.ui.screens.characterdetail

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalUriHandler
import com.danibelmonte.marvelcompose.data.entities.Url

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AppOverflowMenuDetails(urls: List<Url>){

    if(urls.isEmpty()) return

    var showMenu by remember {
        mutableStateOf(false)
    }
    val uriHandler = LocalUriHandler.current
    IconButton(onClick = { showMenu = !showMenu }) {
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = "More Actions"
        )
    }
    DropdownMenu(
        expanded = showMenu,
        onDismissRequest = { showMenu = false }
    ) {
        urls.forEach { externalUrl ->
            DropdownMenuItem(onClick = {
                uriHandler.openUri(externalUrl.destination)
                showMenu = false
            }) {
                ListItem(
                    text = { Text(externalUrl.type) }
                )
            }
        }
    }
}