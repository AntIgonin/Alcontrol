package com.smd.alcontrol.navigation

import alcontrol.composeapp.generated.resources.Res
import alcontrol.composeapp.generated.resources.bottom_bar_calendar
import alcontrol.composeapp.generated.resources.bottom_bar_main
import alcontrol.composeapp.generated.resources.bottom_bar_profile
import com.smd.alcontrol.utils.getStringResource


enum class MainScreens(val route: String, val title: String) {
    General("general", getStringResource(Res.string.bottom_bar_main)),
    Calendar("calendar", getStringResource(Res.string.bottom_bar_calendar)),
    Profile("settings", getStringResource(Res.string.bottom_bar_profile))
}