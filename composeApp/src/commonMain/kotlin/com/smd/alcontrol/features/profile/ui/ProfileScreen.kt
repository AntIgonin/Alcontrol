package com.smd.alcontrol.features.profile.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.smd.alcontrol.di.getMainScope
import com.smd.alcontrol.features.calendar.CalendarViewModel
import com.smd.alcontrol.features.profile.ProfileViewModel
import com.smd.alcontrol.navigation.LocalNavHost
import com.smd.alcontrol.navigation.MainScreens

internal fun NavHostController.openProfileScreen() {
    navigate(MainScreens.Profile.route)
}

internal fun NavGraphBuilder.addProfileScreen() {
    composable(route = MainScreens.Profile.route) {
        ProfileScreen()
    }
}

@Composable
internal fun ProfileScreen(viewModel: ProfileViewModel = viewModel { getMainScope().get() }) {
    val navController = LocalNavHost.current
    val viewState by viewModel.viewStates().collectAsState()
    val viewAction by viewModel.viewActions().collectAsState(null)

    ProfileView(viewState) {
        viewModel.obtainEvent(it)
    }
}