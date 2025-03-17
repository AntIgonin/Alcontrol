package com.smd.alcontrol.features.logdrink.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.smd.alcontrol.di.getMainScope
import com.smd.alcontrol.features.logdrink.LogDrinkAction
import com.smd.alcontrol.features.logdrink.LogDrinkViewModel
import com.smd.alcontrol.features.logdrink.LogDrinkViewState
import com.smd.alcontrol.features.main.openSingleMainScreen
import com.smd.alcontrol.features.splash.SplashAction
import com.smd.alcontrol.features.splash.SplashViewModel
import com.smd.alcontrol.navigation.AppScreen
import com.smd.alcontrol.navigation.LocalNavHost

internal fun NavHostController.openLogDrinkScreen() {
    navigate(AppScreen.LogDrink.route)
}

internal fun NavGraphBuilder.addLogDrinkScreen() {
    composable(route = AppScreen.LogDrink.route) {
        LogDrinkScreen()
    }
}

@Composable
internal fun LogDrinkScreen(viewModel: LogDrinkViewModel = viewModel { getMainScope().get() }) {
    val navController = LocalNavHost.current
    val viewState by viewModel.viewStates().collectAsState()
    val viewAction by viewModel.viewActions().collectAsState(null)

    LogDrinkView(viewState) {
        viewModel.obtainEvent(it)
    }

    when(viewAction) {
        LogDrinkAction.GoToMainScreen -> navController.popBackStack()
        null -> {

        }
    }
}