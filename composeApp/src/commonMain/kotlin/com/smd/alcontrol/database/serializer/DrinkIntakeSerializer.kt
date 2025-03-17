package com.smd.alcontrol.database.serializer

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDateTime

object DrinkIntakeSerializer {

    @TypeConverter
    fun fromLocalDate(localDate: LocalDateTime?): String? {
        return localDate?.toString()
    }

    @TypeConverter
    fun toLocalDate(dateString: String?): LocalDateTime? {
        return dateString?.let { LocalDateTime.parse(it) }
    }
}