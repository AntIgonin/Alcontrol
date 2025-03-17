package com.smd.alcontrol.database.serializer

import androidx.room.TypeConverter
import kotlinx.datetime.DayOfWeek


object DaysOfWeekConverter {
    @TypeConverter
    fun fromHobbies(days: List<DayOfWeek?>): String {
        return days.map { it?.name }.joinToString(",")
    }

    @TypeConverter
    fun toHobbies(data: String): List<DayOfWeek> {
        return data.split(",").map { DayOfWeek.valueOf(it) }
    }
}

