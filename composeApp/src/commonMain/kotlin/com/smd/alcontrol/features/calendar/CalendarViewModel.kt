package com.smd.alcontrol.features.calendar

import androidx.lifecycle.viewModelScope
import com.smd.alcontrol.base.viewmodel.BaseViewModel
import com.smd.alcontrol.common.AlcoholIntoxication
import com.smd.alcontrol.common.model.toUI
import com.smd.alcontrol.database.model.Gender
import com.smd.alcontrol.database.repository.DrinkRepository
import com.smd.alcontrol.database.repository.UserRepository
import com.smd.alcontrol.utils.alcohol.PureAlcoholGm
import com.smd.alcontrol.utils.alcohol.calculatePureAlcoholIntake
import com.smd.alcontrol.utils.alcohol.getIntoxicationLevel
import com.smd.alcontrol.utils.date.minusMonths
import com.smd.alcontrol.utils.date.now
import com.smd.alcontrol.utils.date.plusMonths
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

class CalendarViewModel(
    private val drinkRepository: DrinkRepository,
    private val userRepository: UserRepository
) : BaseViewModel<CalendarViewState, CalendarAction, CalendarViewEvent>(
    initialState = CalendarViewState(
        currentDate = now(),
        selectedDay = now()
    ),
    configureEventHandlersBuilder = {
        listOf(
            CalendarViewEvent.SelectDay::class
        ).immediate()
    }
) {

    init {
        viewModelScope.launch {
            loadStatuses()
            loadIntakes()
        }
    }

    private fun loadStatuses() = viewModelScope.launch {
        val user = userRepository.getUser() ?: throw Exception()
        val monthIntakes = drinkRepository.getDrinksForMonth(viewState.currentDate)
        val dayToStatusMap: Map<Int, AlcoholIntoxication> = monthIntakes.let {
            val dayToPureAlcoholIntake = hashMapOf<Int, PureAlcoholGm>()
            it.forEach { intake ->
                val pureAlcoholIntake = calculatePureAlcoholIntake(intake)
                val alcoholIntakeByDay = dayToPureAlcoholIntake[intake.date.dayOfMonth]
                if (alcoholIntakeByDay == null) {
                    dayToPureAlcoholIntake[intake.date.dayOfMonth] = pureAlcoholIntake
                } else {
                    dayToPureAlcoholIntake[intake.date.dayOfMonth] = alcoholIntakeByDay + pureAlcoholIntake
                }
            }

            return@let dayToPureAlcoholIntake.mapValues { map ->
                getIntoxicationLevel(map.value, user.weight, user.gender == Gender.MALE)
            }
        }
            viewState = viewState.copy(
                dayToStatus = dayToStatusMap
            )
        }

        private fun loadIntakes() = viewModelScope.launch {
            val intakes = drinkRepository.getDrinksForDay(viewState.selectedDay)
            viewState = viewState.copy(
                displayedIntakes = intakes.map { it.toUI() }
            )
        }

        override fun processEvent(viewEvent: CalendarViewEvent) {
            when (viewEvent) {
                CalendarViewEvent.OnNextMonth -> {
                    viewState = viewState.copy(
                        currentDate = viewState.currentDate.plusMonths(1)
                    )
                    loadStatuses()
                }

                CalendarViewEvent.OnPreviousMonth -> {
                    viewState = viewState.copy(
                        currentDate = viewState.currentDate.minusMonths(1)
                    )
                    loadStatuses()
                }

                is CalendarViewEvent.SelectDay -> {
                    viewState = viewState.copy(
                        selectedDay = viewEvent.day
                    )
                    viewModelScope.launch {
                        loadIntakes()
                    }
                }

                is CalendarViewEvent.DeleteIntake -> viewModelScope.launch(Dispatchers.IO) {
                    drinkRepository.deleteDrinkIntakeById(viewEvent.id)
                    loadIntakes()
                    loadStatuses()
                }
            }
        }
    }