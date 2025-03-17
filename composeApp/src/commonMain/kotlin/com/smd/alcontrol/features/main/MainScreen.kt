package com.smd.alcontrol.features.main

import alcontrol.composeapp.generated.resources.Res
import alcontrol.composeapp.generated.resources.ic_calendar
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.smd.alcontrol.features.calendar.ui.addCalendarScreen
import com.smd.alcontrol.features.general.ui.addGeneralScreen
import com.smd.alcontrol.features.profile.ui.addProfileScreen
import com.smd.alcontrol.navigation.AppScreen
import com.smd.alcontrol.navigation.LocalNavHost
import com.smd.alcontrol.navigation.MainScreens
import com.smd.alcontrol.utils.popAllAndNavigate
import org.jetbrains.compose.resources.vectorResource


internal fun NavGraphBuilder.addMainScreen() {
    composable(
        route = AppScreen.Main.route,
    ) {
        MainScreen()
    }
}

internal fun NavHostController.openSingleMainScreen() {
    popAllAndNavigate(AppScreen.Main.route)
}

@Composable
internal fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            NavHost(
                navController,
                startDestination = MainScreens.General.route,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
                popEnterTransition = { EnterTransition.None },
                popExitTransition = { ExitTransition.None },
            ) {
                addGeneralScreen()
                addCalendarScreen()
                addProfileScreen()
            }
        }
    }
}

@Composable
private fun BottomBar(navController: NavController) {
    val outerNavController = LocalNavHost.current
    val items = MainScreens.entries.toTypedArray()

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        items.forEach { screen ->
            val isSelected =
                currentDestination?.hierarchy?.any { it.route == screen.route } == true
            NavigationBarItem(
                icon = {
                    val icon = when (screen) {
                        MainScreens.General -> Icons.Filled.Home
                        MainScreens.Calendar -> vectorResource(Res.drawable.ic_calendar)
                        MainScreens.Profile -> Icons.Filled.Person
                    }
                    Icon(
                        imageVector = icon,
                        contentDescription = screen.route
                    )
                },
                label = {
                    Text(text = screen.title)
                },
                selected = isSelected,
                onClick = {
                    navController.navigate(screen.route)
                }
            )
        }
    }
}