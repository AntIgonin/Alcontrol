package com.smd.alcontrol.database.repository

import com.smd.alcontrol.database.model.DrinkIntake
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month

interface DrinkRepository {
    suspend fun getAllDrinkIntakes(): List<DrinkIntake>?
    suspend fun insertDrinkIntake(drinkIntake: DrinkIntake)
    suspend fun deleteDrinkIntake(drinkIntake: DrinkIntake)
    suspend fun deleteDrinkIntakeById(id: Long)
    suspend fun getDrinksForMonth(monthDate: LocalDate): List<DrinkIntake>
    suspend fun getDrinksForCurrentWeek(): List<DrinkIntake>
    suspend fun getDrinksForDay(day: LocalDate): List<DrinkIntake>
}