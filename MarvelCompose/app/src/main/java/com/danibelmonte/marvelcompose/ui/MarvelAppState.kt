package com.danibelmonte.marvelcompose.ui

import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.danibelmonte.marvelcompose.navigation.NavItem
import com.danibelmonte.marvelcompose.navigation.navigatePoppingUpToStartDestination
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun rememberMarvelAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
): MarvelAppState = remember(scaffoldState, navController, coroutineScope) {
    MarvelAppState(scaffoldState, navController, coroutineScope)
}

class MarvelAppState(
    val scaffoldState: ScaffoldState,
    val navController: NavHostController,
    val coroutineScope: CoroutineScope
) {

    companion object {
        val DrawerOptions = listOf(NavItem.HOME, NavItem.SETTINGS)
        val BottomNavOptions = listOf(NavItem.CHARACTERS, NavItem.COMICS, NavItem.EVENTS)
    }

    val currentRoute: String
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination?.route
            ?: ""

    val showUpNavigation: Boolean
        @Composable get() = currentRoute !in NavItem.values().map { it.navCommand.route }

    val showBottomNavigation: Boolean
        @Composable get() = BottomNavOptions.any { currentRoute.contains(it.navCommand.feature.route) }

    val drawerSelectedIndex: Int
        @Composable get() = if (showBottomNavigation) {
            DrawerOptions.indexOf(NavItem.HOME)
        } else {
            DrawerOptions.indexOfFirst { it.navCommand.route == currentRoute }
        }

    fun onUpClick() {
        navController.popBackStack()
    }

    fun onMenuClick() {
        coroutineScope.launch { scaffoldState.drawerState.open() }
    }

    fun onNavItemClick(item: NavItem) {
        navController.navigatePoppingUpToStartDestination(item.navCommand.route)
    }

    fun onDrawerOptionClick(item: NavItem) {
        coroutineScope.launch {
            scaffoldState.drawerState.close()
        }
        onNavItemClick(item)
    }

}