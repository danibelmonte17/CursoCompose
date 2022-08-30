package com.danibelmonte.marvelcompose.ui.comics

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.danibelmonte.marvelcompose.ui.common.MarvelItemDetailScreen
import com.danibelmonte.marvelcompose.ui.common.MarvelItemsList
import com.danibelmonte.marvelcompose.data.entities.Comic
import com.danibelmonte.marvelcompose.data.repositories.ComicsRepository
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ComicsScreen(onClick: (Comic) -> Unit) {
    var comicsState by remember { mutableStateOf(emptyList<Comic>()) }
    LaunchedEffect(Unit) {
        comicsState = ComicsRepository.get()
    }

    val formats = Comic.Format.values()
    val pagerState = rememberPagerState()

    Column {
        ComicFormatsTabRow(pagerState, formats)
        HorizontalPager(
            count = formats.size,
            state = pagerState
        ) {
            MarvelItemsList(false, comicsState, onClick)
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun ComicFormatsTabRow(
    pagerState: PagerState,
    formats: Array<Comic.Format>
) {
    val scope = rememberCoroutineScope()
    ScrollableTabRow(
        selectedTabIndex = pagerState.currentPage,
        edgePadding = 0.dp,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
            )
        }
    ) {
        formats.forEach {
            Tab(
                selected = it.ordinal == pagerState.currentPage,
                onClick = {
                    scope.launch { pagerState.animateScrollToPage(it.ordinal) }
                },
                text = { Text(stringResource(id = it.stringRes)) }
            )
        }
    }
}

@Composable
fun ComicsDetailsScreen(itemId: Int, onBackAction: () -> Unit) {
    var comicState by remember { mutableStateOf<Comic?>(null) }
    LaunchedEffect(Unit) {
        comicState = ComicsRepository.find(itemId)
    }
    comicState?.let {
        MarvelItemDetailScreen(marvelItem = it, onBackAction = onBackAction)
    }
}