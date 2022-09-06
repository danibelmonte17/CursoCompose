package com.danibelmonte.marvelcompose.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danibelmonte.marvelcompose.MarvelScreen
import com.danibelmonte.marvelcompose.data.entities.MarvelItem
import com.danibelmonte.marvelcompose.data.entities.Result

@Composable
fun <T : MarvelItem> MarvelScreenItems(loading: Boolean, items: Result<List<T>>, onClick: (T) -> Unit) {
    items.fold({ ErrorMessage(it)}){
        MarvelItemsList(loading, it, onClick)
    }

}

@Composable
fun <T : MarvelItem> MarvelItemsList(
    loading: Boolean,
    items: List<T>,
    onClick: (T) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        if(loading){
            CircularProgressIndicator()
        }
        if(items.isNotEmpty()){
            LazyVerticalGrid(
                GridCells.Adaptive(150.dp),
                contentPadding = PaddingValues(4.dp)
            ) {
                this.items(items) {
                    MarvelListItem(
                        marvelItem = it,
                        modifier = Modifier.clickable { onClick(it) }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun MarvelItemsListPreview(){
    MarvelScreen {
        MarvelItemsList(true, items = emptyList(), onClick = {})
    }
}



