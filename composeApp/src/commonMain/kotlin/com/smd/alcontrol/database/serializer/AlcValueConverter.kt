package com.smd.alcontrol.database.serializer

import androidx.room.TypeConverter
import com.smd.alcontrol.common.AlcValue

object AlcValueConverter {
    @TypeConverter
    fun fromAlcValue(alcohol: AlcValue): String {
        return when (alcohol) {
            is AlcValue.Fixed -> "fixed:${alcohol.value}"
            is AlcValue.Range -> "range:${alcohol.min},${alcohol.max}"
        }
    }

    @TypeConverter
    fun toAlcValue(data: String): AlcValue {
        return when {
            data.startsWith("fixed:") -> AlcValue.Fixed(data.removePrefix("fixed:").toDouble())
            data.startsWith("range:") -> {
                val parts = data.removePrefix("range:").split(",")
                AlcValue.Range(parts[0].toDouble(), parts[1].toDouble())
            }
            else -> throw IllegalArgumentException("Unknown AlcValue format")
        }
    }
}