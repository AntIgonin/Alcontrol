package com.smd.alcontrol.utils.date

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime

internal fun now() = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

fun formatDateForUser(dateTime: LocalDate): String {
    val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    val date = dateTime // Берем только дату без времени

    return when {
        date == today -> "Today"
        date == today.minus(1, DateTimeUnit.DAY) -> "Yesterday"
        else -> "${date.dayOfMonth.toString().padStart(2, '0')}.${date.monthNumber.toString().padStart(2, '0')}.${date.year}"
    }
}


fun convertMillisToLocalDateTime(timestampMillis: Long): LocalDateTime {
    val instant = Instant.fromEpochMilliseconds(timestampMillis) // Создаем Instant
    return instant.toLocalDateTime(TimeZone.currentSystemDefault()) // Преобразуем в LocalDateTime
}

fun Month.daysInMonth(year: Int): Int {
    return when (this) {
        Month.JANUARY, Month.MARCH, Month.MAY, Month.JULY, Month.AUGUST, Month.OCTOBER, Month.DECEMBER -> 31
        Month.APRIL, Month.JUNE, Month.SEPTEMBER, Month.NOVEMBER -> 30
        Month.FEBRUARY -> if (isLeapYear(year)) 29 else 28
        else -> 0
    }
}

fun isLeapYear(year: Int): Boolean {
    return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
}

fun LocalDate.minusMonths(months: Int): LocalDate {
    var newYear = this.year
    var newMonth = this.month.number - months
    while (newMonth < 1) {
        newMonth += 12
        newYear -= 1
    }
    return LocalDate(newYear, newMonth, this.dayOfMonth)
}

fun LocalDate.plusMonths(months: Int): LocalDate {
    var newYear = this.year
    var newMonth = this.month.number + months

    while (newMonth > 12) {
        newMonth -= 12
        newYear += 1
    }

    return LocalDate(newYear, newMonth, this.dayOfMonth)
}
