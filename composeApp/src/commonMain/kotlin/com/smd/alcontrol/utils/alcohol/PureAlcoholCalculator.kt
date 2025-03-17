package com.smd.alcontrol.utils.alcohol

import com.smd.alcontrol.common.AlcoholIntoxication
import com.smd.alcontrol.common.AlcoholIntoxication.*
import com.smd.alcontrol.common.value
import com.smd.alcontrol.database.model.DrinkIntake

typealias PureAlcoholGm = Double

const val EthanolDensity = 0.789

internal fun calculatePureAlcoholIntake(drink: DrinkIntake): PureAlcoholGm {
    return (drink.volumeMl * (drink.drink.alcohol.value() / 100) * EthanolDensity)
}

fun calculateMaxAlcoholIntakeForIntoxication(
    intoxication: AlcoholIntoxication = RISK_OF_DEATH,
    weight: Double,
    isMale: Boolean
): PureAlcoholGm {
    val r = if (isMale) 0.68 else 0.55
    val maxBAC = intoxication.minBac

    val maxAlcoholGrams = maxBAC * weight * r

    return maxAlcoholGrams
}


private fun calculatePeakBAC(alcoholGrams: PureAlcoholGm, weight: Double, isMale: Boolean): Double {
    val r = if (isMale) 0.68 else 0.55
    return alcoholGrams / (weight * r)
}

internal fun getIntoxicationLevel(
    alcoholGrams: PureAlcoholGm,
    weight: Double,
    isMale: Boolean
): AlcoholIntoxication {
    val bac = calculatePeakBAC(alcoholGrams, weight, isMale)
    return when (bac) {
        in NONE.minBac .. NONE.maxBac -> NONE
        in SAFE_LEVEL.minBac .. SAFE_LEVEL.maxBac -> SAFE_LEVEL
        in MILD_INTOXICATION.minBac .. MILD_INTOXICATION.maxBac -> MILD_INTOXICATION
        in INTOXICATION.minBac .. INTOXICATION.maxBac -> INTOXICATION
        in SERVE_INTOXICATION.minBac .. SERVE_INTOXICATION.maxBac -> SERVE_INTOXICATION
        in HARD_INTOXICATION.minBac .. HARD_INTOXICATION.maxBac -> HARD_INTOXICATION
        else -> RISK_OF_DEATH
    }
}