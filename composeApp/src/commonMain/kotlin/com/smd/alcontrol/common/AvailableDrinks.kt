package com.smd.alcontrol.common

import alcontrol.composeapp.generated.resources.Res
import alcontrol.composeapp.generated.resources.drink_daiquiri
import alcontrol.composeapp.generated.resources.drink_dipa
import alcontrol.composeapp.generated.resources.drink_fortified_wine
import alcontrol.composeapp.generated.resources.drink_gin
import alcontrol.composeapp.generated.resources.drink_ipa
import alcontrol.composeapp.generated.resources.drink_lager
import alcontrol.composeapp.generated.resources.drink_margarita
import alcontrol.composeapp.generated.resources.drink_martini
import alcontrol.composeapp.generated.resources.drink_mojito
import alcontrol.composeapp.generated.resources.drink_old_fashioned
import alcontrol.composeapp.generated.resources.drink_pilsner
import alcontrol.composeapp.generated.resources.drink_red_wine
import alcontrol.composeapp.generated.resources.drink_rose_wine
import alcontrol.composeapp.generated.resources.drink_rum
import alcontrol.composeapp.generated.resources.drink_sparkling_wine
import alcontrol.composeapp.generated.resources.drink_stout
import alcontrol.composeapp.generated.resources.drink_tequila
import alcontrol.composeapp.generated.resources.drink_vodka
import alcontrol.composeapp.generated.resources.drink_water
import alcontrol.composeapp.generated.resources.drink_whiskey
import alcontrol.composeapp.generated.resources.drink_white_wine
import com.smd.alcontrol.common.AlcValue.Fixed
import com.smd.alcontrol.common.AlcValue.Range
import com.smd.alcontrol.utils.getStringResource
import kotlinx.serialization.Serializable

@Serializable
data class Drink(
    val name: String,
    val alcohol: AlcValue,
    val type: Type
)

@Serializable
sealed class AlcValue {
    data class Fixed(val value: Double) : AlcValue()
    data class Range(val min: Double, val max: Double) : AlcValue()
}
fun AlcValue.value(): Double {
    return when(this) {
        is Fixed -> this.value
        is Range -> (min + max) / 2
    }
}


enum class Type {
    VODKA,
    WHISKEY,
    SPIRITS_COMMON_1,
    SPIRITS_COMMON_2,
    WINE,
    CHAMPAGNE,
    BEER,
    COCKTAIL,
    BOTTLE_WATTER
}

fun vodka() = Drink(getStringResource(Res.string.drink_vodka), AlcValue.Fixed(40.0), Type.VODKA)
fun whiskey() = Drink(getStringResource(Res.string.drink_whiskey), AlcValue.Fixed(40.0), Type.WHISKEY)
fun rum() = Drink(getStringResource(Res.string.drink_rum), AlcValue.Fixed(37.5), Type.SPIRITS_COMMON_2)
fun tequila() =
    Drink(getStringResource(Res.string.drink_tequila), AlcValue.Fixed(38.0), Type.SPIRITS_COMMON_2)
fun gin() = Drink(getStringResource(Res.string.drink_gin), AlcValue.Fixed(37.5), Type.SPIRITS_COMMON_1)

internal fun defaultSpirits() = listOf(vodka(), whiskey(), rum(), tequila(), gin())

fun redWine() = Drink(getStringResource(Res.string.drink_red_wine), AlcValue.Fixed(12.5), Type.WINE)
fun whiteWine() = Drink(getStringResource(Res.string.drink_white_wine), AlcValue.Fixed(12.0), Type.WINE)
fun roseWine() = Drink(getStringResource(Res.string.drink_rose_wine), AlcValue.Fixed(11.5), Type.WINE)
fun sparklingWine() = Drink(getStringResource(Res.string.drink_sparkling_wine), AlcValue.Fixed(12.0), Type.CHAMPAGNE)
fun fortifiedWine() = Drink(getStringResource(Res.string.drink_fortified_wine), AlcValue.Fixed(18.0), Type.WINE)

internal fun wines() = listOf(redWine(), whiteWine(), roseWine(), sparklingWine(), fortifiedWine())

fun lager() = Drink(getStringResource(Res.string.drink_lager), AlcValue.Fixed(5.0), Type.BEER)
fun ipa() = Drink(getStringResource(Res.string.drink_ipa), AlcValue.Fixed(7.0), Type.BEER)
fun dipa() = Drink(getStringResource(Res.string.drink_dipa), AlcValue.Fixed(8.5), Type.BEER)
fun stout() = Drink(getStringResource(Res.string.drink_stout), AlcValue.Fixed(6.5), Type.BEER)
fun pilsner() = Drink(getStringResource(Res.string.drink_pilsner), AlcValue.Fixed(6.5), Type.BEER)


internal fun beers() = listOf(lager(), ipa(), dipa(), stout(), pilsner())

fun martini() = Drink(getStringResource(Res.string.drink_martini), AlcValue.Range(22.0, 25.0), Type.COCKTAIL)
fun margarita() = Drink(getStringResource(Res.string.drink_margarita), AlcValue.Range(15.0, 20.0), Type.COCKTAIL)
fun mojito() = Drink(getStringResource(Res.string.drink_mojito), AlcValue.Range(10.0, 12.0), Type.COCKTAIL)
fun oldFashioned() = Drink(getStringResource(Res.string.drink_old_fashioned), AlcValue.Range(9.0, 11.0), Type.COCKTAIL)
fun daiquiri() = Drink(getStringResource(Res.string.drink_daiquiri), AlcValue.Range(15.0, 18.0), Type.COCKTAIL)

internal fun cocktails() = listOf(martini(), margarita(), mojito(), oldFashioned(), daiquiri())


fun water() = Drink(getStringResource(Res.string.drink_water), AlcValue.Fixed(0.0), Type.BOTTLE_WATTER)

internal fun nonAlcoholDrinks() = listOf(water())

