package com.smd.alcontrol.features.general.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.smd.alcontrol.features.general.GeneralViewState

@Preview
@Composable
fun GeneralViewPreview() {
    GeneralView(GeneralViewState(false, "Антон")) {

    }
}