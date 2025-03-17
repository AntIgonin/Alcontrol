package com.smd.alcontrol.features.splash.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.smd.alcontrol.di.getMainScope
import com.smd.alcontrol.features.main.openSingleMainScreen
import com.smd.alcontrol.features.splash.SplashAction
import com.smd.alcontrol.features.splash.SplashViewModel
import com.smd.alcontrol.navigation.AppScreen
import com.smd.alcontrol.navigation.LocalNavHost

internal fun NavHostController.openSplashScreen() {
    navigate(AppScreen.Splash.route)
}

internal fun NavGraphBuilder.addSplashScreen() {
    composable(route = AppScreen.Splash.route) {
        SplashScreen()
    }
}

@Composable
internal fun SplashScreen(viewModel: SplashViewModel = viewModel { getMainScope().get() }) {
    val navController = LocalNavHost.current
    val viewState by viewModel.viewStates().collectAsState()
    val viewAction by viewModel.viewActions().collectAsState(null)

    when(viewAction) {
        SplashAction.GoToMainScreen -> navController.openSingleMainScreen()
        null -> {

        }
    }
}