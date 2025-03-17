package com.smd.alcontrol.features.calendar

import com.smd.alcontrol.common.AlcoholIntoxication
import com.smd.alcontrol.common.model.DrinkIntakeUI
import kotlinx.datetime.LocalDate

data class CalendarViewState(
    val isLoading: Boolean = false,
    val currentDate: LocalDate,
    val selectedDay: LocalDate,
    val displayedIntakes: List<DrinkIntakeUI> = listOf(),
    val dayToStatus: Map<Int, AlcoholIntoxication> = hashMapOf()
)

sealed class CalendarViewEvent {
    data object OnNextMonth: CalendarViewEvent()
    data object OnPreviousMonth: CalendarViewEvent()
    data class SelectDay(val day: LocalDate): CalendarViewEvent()
    data class DeleteIntake(val id: Long): CalendarViewEvent()
}

sealed class CalendarAction {

}