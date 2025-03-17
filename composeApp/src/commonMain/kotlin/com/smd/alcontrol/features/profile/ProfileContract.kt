package com.smd.alcontrol.features.profile

import com.smd.alcontrol.database.model.Gender
import com.smd.alcontrol.utils.alcohol.PureAlcoholGm
import kotlinx.datetime.DayOfWeek

data class ProfileViewState(
    val isLoading: Boolean = false,
    val name: String? = null,
    val age: Int? = null,
    val ageLabel: String? = null,
    val weight: Double? = null,
    val weightLabel: String? = null,
    val gender: Gender? = null,
    val genderLabel: String? = null,
    val avgPureAlcoholConsumption: PureAlcoholGm? = null,
    val avgPureAlcoholConsumptionLabel: String? = null,
    val daysOfConsumption: List<DayOfWeek> = listOf(),
    val daysOfConsumptionLabel: String? = null
)

sealed class ProfileViewEvent {

}

sealed class ProfileAction {

}