package com.smd.alcontrol.features.general.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.smd.alcontrol.di.getMainScope
import com.smd.alcontrol.features.general.GeneralAction
import com.smd.alcontrol.features.general.GeneralViewModel
import com.smd.alcontrol.features.logdrink.ui.openLogDrinkScreen
import com.smd.alcontrol.navigation.LocalNavHost
import com.smd.alcontrol.navigation.MainScreens

internal fun NavHostController.openGeneralScreen() {
    navigate(MainScreens.General.route)
}

internal fun NavGraphBuilder.addGeneralScreen() {
    composable(route = MainScreens.General.route) {
        GeneralScreen()
    }
}

@Composable
internal fun GeneralScreen(viewModel: GeneralViewModel = viewModel { getMainScope().get() }) {
    val navController = LocalNavHost.current
    val viewState by viewModel.viewStates().collectAsState()
    val viewAction by viewModel.viewActions().collectAsState(null)

    GeneralView(viewState) {
        viewModel.obtainEvent(it)
    }

    when(viewAction){
        GeneralAction.OpenLogDrinkScreen -> {
            viewModel.clearAction()
            navController.openLogDrinkScreen()
        }
        else -> {}
    }
}