package com.danibelmonte.marvelcompose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.danibelmonte.marvelcompose.ui.common.AppBarIcons
import com.danibelmonte.marvelcompose.ui.common.AppBottomNavigation
import com.danibelmonte.marvelcompose.ui.common.AppDrawerOption
import com.danibelmonte.marvelcompose.navigation.NavigationComposable
import com.danibelmonte.marvelcompose.ui.MarvelAppState
import com.danibelmonte.marvelcompose.ui.rememberMarvelAppState
import com.danibelmonte.marvelcompose.ui.theme.MarvelComposeTheme

@Composable
fun MarvelApp() {

    val appState = rememberMarvelAppState()

    MarvelScreen {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(id = R.string.app_name)) },
                    navigationIcon = {
                        if (appState.showUpNavigation) {
                            AppBarIcons(
                                imageVector = Icons.Default.ArrowBack,
                                onClick = {
                                    appState.onUpClick()
                                }
                            )
                        } else {
                            AppBarIcons(
                                imageVector = Icons.Default.Menu,
                                onClick = {
                                    appState.onMenuClick()
                                }
                            )
                        }
                    }
                )
            },
            drawerContent = {
                AppDrawerOption(
                    MarvelAppState.DrawerOptions,
                    selectedIndex = appState.drawerSelectedIndex,
                    onClickOption = { item -> appState.onDrawerOptionClick(item) }
                )
            },
            bottomBar = {
                if (appState.showBottomNavigation) {
                    AppBottomNavigation(
                        MarvelAppState.BottomNavOptions,
                        appState.currentRoute
                    ) { item -> appState.onNavItemClick(item) }
                }
            },
            scaffoldState = appState.scaffoldState
        ) { padding ->
            Box(modifier = Modifier.padding(padding)) {
                NavigationComposable(appState.navController)
            }
        }
    }
}

@Composable
fun MarvelScreen(content: @Composable () -> Unit) {
    MarvelComposeTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            content()
        }
    }
}