package com.danibelmonte.marvelcompose.common.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danibelmonte.marvelcompose.MarvelScreen
import com.danibelmonte.marvelcompose.navigation.NavItem

@Composable
fun AppDrawerOption(
    drawerOptions: List<NavItem>,
    selectedIndex: Int,
    onClickOption: (NavItem) -> Unit
) {
    Box(
        modifier = Modifier
            .background(
                Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colors.primaryVariant,
                        MaterialTheme.colors.secondary
                    )
                )
            )
            .height(200.dp)
            .fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(16.dp))
    drawerOptions.forEachIndexed { index, navItem ->
        val selected = selectedIndex == index
        val color = MaterialTheme.colors
        Row(
            modifier = Modifier
                .clickable { onClickOption(navItem) }
                .fillMaxWidth()
                .padding(8.dp, 4.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(
                    color = if (selected) {
                        color.primary.copy(alpha = 0.12f)
                    } else {
                        color.surface
                    }
                )
                .padding(12.dp)
        ) {
            Icon(
                imageVector = navItem.icon,
                tint = if(selected) color.primary else color.onSurface.copy(
                    alpha = ContentAlpha.medium
                ),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(24.dp))
            Text(
                text = stringResource(id = navItem.title),
                style = MaterialTheme.typography.body1.copy(
                    fontWeight = FontWeight.Bold,
                    color = if(selected) color.primary else color.onSurface
                )
            )
        }
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun DrawerContentPreview() {
    MarvelScreen {
        Column {
            AppDrawerOption(
                drawerOptions = listOf(NavItem.HOME, NavItem.SETTINGS),
                selectedIndex = 0,
                onClickOption = {})
        }
    }
}