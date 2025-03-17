package com.smd.alcontrol.database.repository

import com.smd.alcontrol.database.dao.DrinkDao
import com.smd.alcontrol.database.model.DrinkIntake
import com.smd.alcontrol.utils.date.daysInMonth
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

class DrinkRepositoryImpl(private val drinkDao: DrinkDao) : DrinkRepository {
    override suspend fun getAllDrinkIntakes(): List<DrinkIntake> = drinkDao.getAllDrinkIntakes()

    override suspend fun insertDrinkIntake(drinkIntake: DrinkIntake) =
        drinkDao.insertDrinkIntake(drinkIntake)

    override suspend fun deleteDrinkIntake(drinkIntake: DrinkIntake) =
        drinkDao.deleteDrinkIntake(drinkIntake)

    override suspend fun deleteDrinkIntakeById(id: Long) {
        drinkDao.deleteById(id)
    }

    override suspend fun getDrinksForMonth(monthDate: LocalDate): List<DrinkIntake> {
        val startOfMonth = LocalDate(monthDate.year, monthDate.month, 1)
        val endOfMonth =
            LocalDate(monthDate.year, monthDate.month, monthDate.month.daysInMonth(monthDate.year))
        return drinkDao.getDrinksByDate(startOfMonth.toString(), endOfMonth.toString())
    }

    override suspend fun getDrinksForCurrentWeek(
    ): List<DrinkIntake> {
        val now = Clock.System.now().toLocalDateTime(TimeZone.UTC)

        val startOfWeek = now.date.minus(now.dayOfWeek.ordinal, DateTimeUnit.DAY) // Понедельник
            .atTime(0, 0, 0)

        val endOfWeek = startOfWeek.date.plus(6, DateTimeUnit.DAY) // Воскресенье
            .atTime(23, 59, 59)

        return drinkDao.getDrinksByDate(startOfWeek.toString(), endOfWeek.toString())
    }

    override suspend fun getDrinksForDay(day: LocalDate): List<DrinkIntake> {
        val startOfDay = day.atTime(0, 0, 0)
        val endOfDay = day.atTime(23, 59, 59)
        return drinkDao.getDrinksByDate(startOfDay.toString(), endOfDay.toString())
    }
}