package com.smd.alcontrol.features.calendar.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.smd.alcontrol.di.getMainScope
import com.smd.alcontrol.features.calendar.CalendarViewModel
import com.smd.alcontrol.navigation.LocalNavHost
import com.smd.alcontrol.navigation.MainScreens

internal fun NavHostController.openCalendarScreen() {
    navigate(MainScreens.Calendar.route)
}

internal fun NavGraphBuilder.addCalendarScreen() {
    composable(route = MainScreens.Calendar.route) {
        CalendarScreen()
    }
}

@Composable
internal fun CalendarScreen(viewModel: CalendarViewModel = viewModel { getMainScope().get() }) {
    val navController = LocalNavHost.current
    val viewState by viewModel.viewStates().collectAsState()
    val viewAction by viewModel.viewActions().collectAsState(null)

    CalendarView(viewState) {
        viewModel.obtainEvent(it)
    }
}