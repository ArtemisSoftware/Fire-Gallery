package com.artemissoftware.common.composables.navigation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.artemissoftware.common.composables.navigation.models.BaseDestinations
import com.artemissoftware.common.composables.navigation.models.BottomBarItem
import com.artemissoftware.common.composables.scaffold.models.FGScaffoldState

@Composable
fun FGBottomNavigationBar (
    items: List<BottomBarItem>,
    navController: NavHostController? = null,
    modifier: Modifier = Modifier,
    fgScaffoldState: FGScaffoldState? = null,
) {

    navController?.let {
        if (items.isNotEmpty()) {

            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination


            if (showBottomBar(currentDestination, items = items)) {


                fgScaffoldState?.let {
                    FGNavigationBar(
                        modifier = modifier,
                        items = items,
                        selectedDestination = it.getSelectedBottomBarDestination(items[0].destination),
                        onClick = { item->

                            it.changeCurrentPositionBottomBar(
                                destination = item.destination,
                                navController = navController
                            )
//                        navController.navigate(item.route) {
//
//                            popUpTo(navController.graph.findStartDestination().id)
//                            launchSingleTop = true
//                        }
                        }
                    )

                }




            }
        }
    }
}


@Composable
private fun FGNavigationBar (
    modifier: Modifier = Modifier,
    items: List<BottomBarItem>,
    selectedDestination: BaseDestinations,
    onClick: (BottomBarItem) -> Unit
) {

    Box(
        modifier = modifier
            .shadow(5.dp)
            .background(color = MaterialTheme.colors.surface)
            .height(64.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            for (item in items) {
                val isSelected = item.destination.route == selectedDestination.route
                val animatedWeight by animateFloatAsState(targetValue = if (isSelected) 1.5f else 1f)

                Box(
                    modifier = Modifier.weight(animatedWeight),
                    contentAlignment = Alignment.Center,
                ) {

                    FGBottomNavigationItem(
                        modifier = Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { onClick.invoke(item) }
                        ),
                        item = item,
                        isSelected = isSelected
                    )
                }
            }
        }
    }
}



private fun showBottomBar(
    currentDestination: NavDestination?,
    items: List<BottomBarItem>) = items.any { it.destination.getRoutel() == currentDestination?.route }


@Preview(showBackground = false)
@Composable
private fun FGNavigationBarPreview() {
    class TestDestination: BaseDestinations(route = "Create")

    val list = listOf(
        BottomBarItem(com.artemissoftware.common.R.string.confirm, Icons.Filled.Create, Icons.Outlined.Create, TestDestination()),
        BottomBarItem(com.artemissoftware.common.R.string.confirm, Icons.Filled.Person, Icons.Outlined.Person, TestDestination())
    )

    FGNavigationBar(
        items = list,
        selectedDestination = list[0].destination,
        onClick = {}
    )
}

@Preview(showBackground = false)
@Composable
private fun FGBottomNavigationBarPreview() {

    class TestDestination: BaseDestinations(route = "Create")

    val list = listOf(
        BottomBarItem(com.artemissoftware.common.R.string.confirm, Icons.Filled.Create, Icons.Outlined.Create, TestDestination()),
        BottomBarItem(com.artemissoftware.common.R.string.confirm, Icons.Filled.Person, Icons.Outlined.Person, TestDestination())
    )

    FGBottomNavigationBar(
        items = list,
        rememberNavController(),
    )
}
