package com.danibelmonte.marvelcompose.ui.common

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun AppBarIcons(imageVector: ImageVector, contentDescription: String? = null, onClick: () -> Unit){
    IconButton(onClick = onClick) {
        Icon(imageVector = imageVector, contentDescription = contentDescription )
    }
}