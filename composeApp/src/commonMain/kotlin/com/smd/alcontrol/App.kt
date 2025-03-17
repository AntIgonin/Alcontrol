package com.smd.alcontrol

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.smd.alcontrol.navigation.AppScreen
import androidx.navigation.compose.NavHost
import com.smd.alcontrol.features.logdrink.ui.addLogDrinkScreen
import com.smd.alcontrol.features.main.addMainScreen
import com.smd.alcontrol.features.profile.ui.addProfileScreen
import com.smd.alcontrol.features.splash.ui.addSplashScreen
import com.smd.alcontrol.navigation.LocalNavHost
import org.jetbrains.compose.ui.tooling.preview.Preview
import com.smd.alcontrol.theme.AppTheme

@Composable
@Preview
fun App() = AppTheme {
    val navController: NavHostController = rememberNavController()
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        CompositionLocalProvider(
            LocalNavHost provides navController
        ) {
            NavHost(
                navController = navController,
                startDestination = AppScreen.Splash.route
            ) {
                addSplashScreen()
                addMainScreen()
                addLogDrinkScreen()
            }
        }
    }
}