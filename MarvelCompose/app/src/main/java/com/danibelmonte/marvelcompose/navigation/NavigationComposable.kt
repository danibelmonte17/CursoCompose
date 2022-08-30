package com.danibelmonte.marvelcompose.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.danibelmonte.marvelcompose.ui.comics.ComicsDetailsScreen
import com.danibelmonte.marvelcompose.ui.comics.ComicsScreen
import com.danibelmonte.marvelcompose.ui.events.EventsDetailsScreen
import com.danibelmonte.marvelcompose.ui.events.EventsScreen
import com.danibelmonte.marvelcompose.ui.characters.CharacterDetailScreen
import com.danibelmonte.marvelcompose.ui.characters.CharactersScreen

@Composable
fun NavigationComposable(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Feature.CHARACTERS.route
    ){
        charactersNavGraph(navController)
        comicsNavGraph(navController)
        eventsNavGraph(navController)
        composable(NavCommand.ContentType(Feature.SETTINGS)){
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ){
                Text("Settings", style = MaterialTheme.typography.h3)
            }
        }
    }
}

private fun NavGraphBuilder.charactersNavGraph(navController: NavHostController) {
    navigation(
        startDestination = NavCommand.ContentType(Feature.CHARACTERS).route,
        route = Feature.CHARACTERS.route
    ){
        composable(NavCommand.ContentType(Feature.CHARACTERS)) {
            CharactersScreen { character ->
                navController.navigate(
                    NavCommand.ContentDetails(Feature.CHARACTERS).createRoute(character.id)
                )
            }
        }
        composable(NavCommand.ContentDetails(Feature.CHARACTERS)) {
            CharacterDetailScreen {
                navController.popBackStack()
            }
        }
    }
}

private fun NavGraphBuilder.comicsNavGraph(navController: NavHostController) {
    navigation(
        startDestination = NavCommand.ContentType(Feature.COMICS).route,
        route = Feature.COMICS.route
    ){
        composable(NavCommand.ContentType(Feature.COMICS)){
            ComicsScreen{ comic ->
                navController.navigate(
                    NavCommand.ContentDetails(Feature.COMICS).createRoute(comic.id)
                )
            }
        }
        composable(NavCommand.ContentDetails(Feature.COMICS)){
            ComicsDetailsScreen(it.findArg(NavArgs.ItemId)) {
                navController.popBackStack()
            }
        }
    }
}

private fun NavGraphBuilder.eventsNavGraph(navController: NavHostController) {
    navigation(
        startDestination = NavCommand.ContentType(Feature.EVENTS).route,
        route = Feature.EVENTS.route
    ){
        composable(NavCommand.ContentType(Feature.EVENTS)){
            EventsScreen{ event ->
                navController.navigate(
                    NavCommand.ContentDetails(Feature.EVENTS).createRoute(event.id)
                )
            }
        }
        composable(NavCommand.ContentDetails(Feature.EVENTS)){
            EventsDetailsScreen(it.findArg(NavArgs.ItemId)) {
                navController.popBackStack()
            }
        }
    }
}

private fun NavGraphBuilder.composable(
    navCommand: NavCommand,
    content: @Composable (NavBackStackEntry) -> Unit
){
    composable(
        route = navCommand.route,
        arguments = navCommand.args
    ){
        content(it)
    }
}

private inline fun <reified T> NavBackStackEntry.findArg(arg: NavArgs): T {
    val value = arguments?.get(arg.key)
    requireNotNull(value)
    return value as T
}

