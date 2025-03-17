package com.smd.alcontrol.utils

import androidx.navigation.NavHostController

internal fun NavHostController.popAll() {
    var popSuccess: Boolean
    do {
        popSuccess = popBackStack()
    } while (popSuccess)
}

internal fun NavHostController.popAllAndNavigate(route: String) {
    popAll()
    navigate(route)
}
