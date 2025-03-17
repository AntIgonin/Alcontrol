package com.smd.alcontrol.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.smd.alcontrol.database.dao.DrinkDao
import com.smd.alcontrol.database.dao.UserDao
import com.smd.alcontrol.database.model.DrinkIntake
import com.smd.alcontrol.database.model.User
import com.smd.alcontrol.database.serializer.AlcValueConverter
import com.smd.alcontrol.database.serializer.DaysOfWeekConverter
import com.smd.alcontrol.database.serializer.DrinkIntakeSerializer

@Database(entities = [DrinkIntake::class, User::class], version = 1)
@TypeConverters(DrinkIntakeSerializer::class, AlcValueConverter::class, DaysOfWeekConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun drinkDao(): DrinkDao
    abstract fun userDao(): UserDao
}

internal const val dbFileName = "alcontrol.db"
