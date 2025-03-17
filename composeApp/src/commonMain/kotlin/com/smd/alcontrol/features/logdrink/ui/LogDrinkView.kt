package com.smd.alcontrol.features.logdrink.ui

import androidx.compose.runtime.Composable
import com.smd.alcontrol.features.logdrink.LogDrinkEvent
import com.smd.alcontrol.features.logdrink.LogDrinkViewState

@Composable
fun LogDrinkView(viewState: LogDrinkViewState, obtainEvent: (LogDrinkEvent) -> Unit) {
    if (viewState.selectDrinkData != null) {
        DrinksCarousel(viewState.selectDrinkData, obtainEvent)
    }
    if (viewState.selectDrinkVolumeData != null) {
        DrinksVolumeView(viewState.selectDrinkVolumeData, obtainEvent)
    }
}
