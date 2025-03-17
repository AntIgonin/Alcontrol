package com.smd.alcontrol.common.model

import alcontrol.composeapp.generated.resources.Res
import alcontrol.composeapp.generated.resources.ic_bottle_of_water
import alcontrol.composeapp.generated.resources.ic_champagne
import alcontrol.composeapp.generated.resources.ic_cocktail
import alcontrol.composeapp.generated.resources.ic_pint_of_beer
import alcontrol.composeapp.generated.resources.ic_spirits_common_1
import alcontrol.composeapp.generated.resources.ic_spirits_common_2
import alcontrol.composeapp.generated.resources.ic_vodka
import alcontrol.composeapp.generated.resources.ic_whiskey
import alcontrol.composeapp.generated.resources.ic_wine_bottle
import com.smd.alcontrol.common.AlcValue
import com.smd.alcontrol.common.Drink
import com.smd.alcontrol.common.Type
import org.jetbrains.compose.resources.DrawableResource

data class DrinkUI (
    val name: String,
    val alcValue: AlcValue,
    val type: Type,
    val iconRes: DrawableResource
) {
    val alcohol = alcValue.toUI()
}

fun Drink.toUI(): DrinkUI = DrinkUI(this.name, this.alcohol, this.type, mapTypeToDrawable(this.type))

internal fun AlcValue.toUI(): String {
    return when(this) {
        is AlcValue.Fixed -> "${this.value}°"
        is AlcValue.Range -> "${this.min}° - ${this.max}°"
    }
}

internal fun mapTypeToDrawable(type: Type): DrawableResource {
    return when(type) {
        Type.VODKA -> Res.drawable.ic_vodka
        Type.WHISKEY -> Res.drawable.ic_whiskey
        Type.SPIRITS_COMMON_1 -> Res.drawable.ic_spirits_common_1
        Type.SPIRITS_COMMON_2 -> Res.drawable.ic_spirits_common_2
        Type.WINE -> Res.drawable.ic_wine_bottle
        Type.CHAMPAGNE -> Res.drawable.ic_champagne
        Type.BEER -> Res.drawable.ic_pint_of_beer
        Type.COCKTAIL -> Res.drawable.ic_cocktail
        Type.BOTTLE_WATTER -> Res.drawable.ic_bottle_of_water
    }
}