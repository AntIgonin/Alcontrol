package com.smd.alcontrol.utils.color

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import com.smd.alcontrol.common.AlcoholIntoxication
import com.smd.alcontrol.theme.danger_alc_status
import com.smd.alcontrol.theme.normal_alc_status
import com.smd.alcontrol.theme.safe_alc_status

fun mapAlcoholIntoxicationToColor(status: AlcoholIntoxication): Color {
    return when(status) {
        AlcoholIntoxication.SAFE_LEVEL -> safe_alc_status
        AlcoholIntoxication.MILD_INTOXICATION -> normal_alc_status
        AlcoholIntoxication.INTOXICATION -> normal_alc_status
        AlcoholIntoxication.SERVE_INTOXICATION -> danger_alc_status
        AlcoholIntoxication.HARD_INTOXICATION -> danger_alc_status
        AlcoholIntoxication.RISK_OF_DEATH -> danger_alc_status
        AlcoholIntoxication.NONE -> safe_alc_status
    }
}