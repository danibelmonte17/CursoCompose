package com.danibelmonte.marvelcompose.ui.common

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.danibelmonte.marvelcompose.navigation.NavItem

@Composable
fun AppBottomNavigation(
    bottomNavOptions: List<NavItem>,
    currentRoute: String,
    onClick: (NavItem) -> Unit
) {
    BottomNavigation {
        bottomNavOptions.forEach { item ->
            val title = stringResource(id = item.title)
            BottomNavigationItem(
                selected = currentRoute.contains(item.navCommand.feature.route),
                onClick = { onClick(item) },
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