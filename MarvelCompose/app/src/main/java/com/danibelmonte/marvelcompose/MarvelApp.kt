package com.danibelmonte.marvelcompose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.danibelmonte.marvelcompose.common.ui.AppBarIcons
import com.danibelmonte.marvelcompose.common.ui.AppBottomNavigation
import com.danibelmonte.marvelcompose.common.ui.AppDrawerOption
import com.danibelmonte.marvelcompose.navigation.NavItem
import com.danibelmonte.marvelcompose.navigation.NavigationComposable
import com.danibelmonte.marvelcompose.navigation.navigatePoppingUpToStartDestination
import com.danibelmonte.marvelcompose.ui.theme.MarvelComposeTheme
import kotlinx.coroutines.launch

@Composable
fun MarvelApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: ""
    val showUpNavigation = currentRoute !in NavItem.values().map { it.navCommand.route }

    val scaffoldState = rememberScaffoldState()

    val scope = rememberCoroutineScope()

    val drawerOptions = listOf(NavItem.HOME, NavItem.SETTINGS)
    val bottomNavOptions = listOf(NavItem.CHARACTERS, NavItem.COMICS, NavItem.EVENTS)

    val showBottomNavigation =
        bottomNavOptions.any { currentRoute.contains(it.navCommand.feature.route) }
    val drawerSelectedIndex = if(showBottomNavigation){
        drawerOptions.indexOf(NavItem.HOME)
    } else {
        drawerOptions.indexOfFirst { it.navCommand.route==currentRoute }
    }

    MarvelScreen {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(id = R.string.app_name)) },
                    navigationIcon = {
                        if (showUpNavigation) {
                            AppBarIcons(imageVector = Icons.Default.ArrowBack) {
                                navController.popBackStack()
                            }
                        } else {
                            AppBarIcons(
                                imageVector = Icons.Default.Menu,
                                onClick = {
                                    scope.launch { scaffoldState.drawerState.open() }
                                }
                            )
                        }
                    }
                )
            },
            drawerContent = {
                AppDrawerOption(drawerOptions, selectedIndex = drawerSelectedIndex) { item ->
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }
                    navController.navigate(item.navCommand.route)
                }
            },
            bottomBar = {
                if (showBottomNavigation) {
                    AppBottomNavigation(bottomNavOptions, currentRoute) {
                        navController.navigatePoppingUpToStartDestination(it.navCommand.route)
                    }
                }
            },
            scaffoldState = scaffoldState
        ) { padding ->
            Box(modifier = Modifier.padding(padding)) {
                NavigationComposable(navController)
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