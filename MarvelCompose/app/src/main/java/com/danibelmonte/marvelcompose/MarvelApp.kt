package com.danibelmonte.marvelcompose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.danibelmonte.marvelcompose.common.ui.AppBarIcons
import com.danibelmonte.marvelcompose.navigation.NavItem
import com.danibelmonte.marvelcompose.navigation.NavigationComposable
import com.danibelmonte.marvelcompose.navigation.navigatePoppingUpToStartDestination
import com.danibelmonte.marvelcompose.ui.theme.MarvelComposeTheme

@Composable
fun MarvelApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: ""
    val showUpNavigation = currentRoute !in NavItem.values().map { it.navCommand.route }
    MarvelScreen {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(id = R.string.app_name)) },
                    navigationIcon = if (showUpNavigation) {
                        {
                            AppBarIcons(imageVector = Icons.Default.ArrowBack) {
                                navController.popBackStack()
                            }
                        }
                    } else null
                )
            },
            bottomBar = {
                BottomNavigation {
                    NavItem.values().forEach { item ->
                        val title = stringResource(id = item.title)
                        BottomNavigationItem(
                            selected = currentRoute.contains(item.navCommand.feature.route),
                            onClick = { navController.navigatePoppingUpToStartDestination(item.navCommand.route) },
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = title
                                )
                            },
                            label = { Text(title) }
                        )
                    }
                }
            }
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