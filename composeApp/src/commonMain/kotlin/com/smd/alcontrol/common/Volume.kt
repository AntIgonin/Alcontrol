package com.smd.alcontrol.common

typealias DrinkVolume = Int

fun getAvailableVolumesByType(type: Type): List<DrinkVolume> {
    return when(type) {
        Type.VODKA -> listOf(50, )
        Type.WHISKEY -> TODO()
        Type.SPIRITS_COMMON_1 -> TODO()
        Type.SPIRITS_COMMON_2 -> TODO()
        Type.WINE -> TODO()
        Type.CHAMPAGNE -> TODO()
        Type.BEER -> TODO()
        Type.COCKTAIL -> TODO()
        Type.BOTTLE_WATTER -> TODO()
    }
}