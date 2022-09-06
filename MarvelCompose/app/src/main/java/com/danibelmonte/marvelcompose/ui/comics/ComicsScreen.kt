package com.danibelmonte.marvelcompose.ui.comics

import androidx.compose.foundation.layout.Column
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.danibelmonte.marvelcompose.data.entities.Comic
import com.danibelmonte.marvelcompose.ui.common.ErrorMessage
import com.danibelmonte.marvelcompose.ui.common.MarvelItemDetailScreen
import com.danibelmonte.marvelcompose.ui.common.MarvelItemsList
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ComicsScreen(viewModel: ComicsViewModel = viewModel(), onClick: (Comic) -> Unit) {

    val formats = Comic.Format.values()
    val pagerState = rememberPagerState()

    Column {
        ComicFormatsTabRow(pagerState, formats)
        HorizontalPager(
            count = formats.size,
            state = pagerState
        ) { page ->
            val format = formats[page]
            viewModel.formatRequested(format)
            val pageState = viewModel.state.getValue(format).value

            pageState.items.fold({ ErrorMessage(error = it)}){ items ->
                MarvelItemsList(
                    pageState.loading,
                    items,
                    onClick
                )
            }

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
fun ComicsDetailsScreen(viewModel: ComicDetailsViewModel =  viewModel(), onBackAction: () -> Unit) {
    val state by viewModel.state.collectAsState()
    MarvelItemDetailScreen(
        loading = state.loading,
        marvelItem = state.comic,
        onBackAction = onBackAction
    )
}