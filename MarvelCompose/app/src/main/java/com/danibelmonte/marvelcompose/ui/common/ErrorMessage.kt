package com.danibelmonte.marvelcompose.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.danibelmonte.marvelcompose.data.entities.Error

@Composable
fun ErrorMessage(error: Error) {
    val message = when (error) {
        is Error.Connectivity -> "Connectivity Error"
        is Error.Server -> "Server error: ${error.code}"
        is Error.Unknown -> "Unknown error: ${error.message}"
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = message,
            modifier = Modifier.size(128.dp),
            tint = MaterialTheme.colors.error
        )
        Text(message, textAlign = TextAlign.Center, style = MaterialTheme.typography.h4)
    }
}