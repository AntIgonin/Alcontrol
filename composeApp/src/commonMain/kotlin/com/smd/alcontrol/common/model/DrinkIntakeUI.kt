package com.smd.alcontrol.common.model

import com.smd.alcontrol.database.model.DrinkIntake

data class DrinkIntakeUI(
    val id: Long,
    val drink: DrinkUI,
    val volumeML: Int
)

internal fun DrinkIntake.toUI() = DrinkIntakeUI(
    this.id,
    this.drink.toUI(),
    this.volumeMl
)
