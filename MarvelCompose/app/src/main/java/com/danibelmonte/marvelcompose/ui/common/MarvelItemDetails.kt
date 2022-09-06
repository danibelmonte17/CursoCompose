package com.danibelmonte.marvelcompose.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.danibelmonte.marvelcompose.data.entities.MarvelItem
import com.danibelmonte.marvelcompose.data.entities.Reference
import com.danibelmonte.marvelcompose.data.entities.ReferenceList
import com.danibelmonte.marvelcompose.data.entities.Result


@Composable
fun MarvelItemDetailScreen(loading: Boolean = false, marvelItem: Result<MarvelItem?>, onBackAction: () -> Unit) {

    if(loading){
        CircularProgressIndicator()
    }

    marvelItem.fold({ ErrorMessage(error = it)}){ item ->
        if(item!=null){
            MarvelItemDetailsScafold(
                item,
                onBackAction
            ) { paddingValues ->
                LazyColumn(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxWidth()
                ) {
                    item {
                        Header(item)
                    }
                    section(Icons.Default.Book, "Series", item.references.first { it.type == ReferenceList.Type.SERIES }.references)
                    item.references.firstOrNull { it.type == ReferenceList.Type.EVENT }?.let {
                        section(Icons.Default.Book, "Events", it.references)
                    }
                    item.references.firstOrNull { it.type == ReferenceList.Type.COMIC }?.let {
                        section(Icons.Default.Book, "Comics", it.references)
                    }
                    section(Icons.Default.Book, "Stories", item.references.first { it.type == ReferenceList.Type.STORY }.references)
                    item {
                        Spacer(modifier = Modifier.height(56.dp))
                    }
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterialApi::class)
fun LazyListScope.section(icon: ImageVector, name: String, listReference: List<Reference>) {
    if(listReference.isNotEmpty()){
        item {
            Text(
                text = name,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(16.dp)
            )
        }
        items(listReference){
            ListItem(
                text = { Text(it.name) },
                icon = { Icon(imageVector = icon, contentDescription = null) }
            )
        }
        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun Header(marvelItem: MarvelItem) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = marvelItem.thumbnail),
            contentDescription = marvelItem.description,
            modifier = Modifier
                .background(Color.LightGray)
                .fillMaxWidth()
                .aspectRatio(1f),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = marvelItem.title,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h4,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = marvelItem.description ?: "",
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp)
        )
    }
}