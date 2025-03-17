package com.smd.alcontrol.features.general

import alcontrol.composeapp.generated.resources.Res
import alcontrol.composeapp.generated.resources.general_bottom_sheet_select_drinks
import alcontrol.composeapp.generated.resources.general_bottom_sheet_select_volume
import alcontrol.composeapp.generated.resources.general_screen_hello
import alcontrol.composeapp.generated.resources.general_screen_hello_template
import androidx.lifecycle.viewModelScope
import com.smd.alcontrol.base.viewmodel.BaseViewModel
import com.smd.alcontrol.common.AlcValue
import com.smd.alcontrol.common.AlcoholIntoxication
import com.smd.alcontrol.common.Drink
import com.smd.alcontrol.common.Type
import com.smd.alcontrol.database.model.DrinkIntake
import com.smd.alcontrol.database.model.Gender
import com.smd.alcontrol.database.model.User
import com.smd.alcontrol.database.repository.DrinkRepository
import com.smd.alcontrol.database.repository.UserRepository
import com.smd.alcontrol.utils.alcohol.PureAlcoholGm
import com.smd.alcontrol.utils.alcohol.calculateMaxAlcoholIntakeForIntoxication
import com.smd.alcontrol.utils.alcohol.calculatePureAlcoholIntake
import com.smd.alcontrol.utils.alcohol.getIntoxicationLevel
import com.smd.alcontrol.utils.date.formatDateForUser
import com.smd.alcontrol.utils.getStringResource
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime


class GeneralViewModel(
    private val drinkRepository: DrinkRepository,
    private val userRepository: UserRepository
) : BaseViewModel<GeneralViewState, GeneralAction, GeneralEvent>(
    initialState = GeneralViewState(
        isLoading = true,
        helloLabel = getStringResource(Res.string.general_screen_hello)
    )
) {

    init {
        loadGeneralInfo()
    }

    private fun loadGeneralInfo() = viewModelScope.launch {
        val profile = userRepository.getUser() ?: throw Exception("User need to be not null")

        val intakes = drinkRepository.getDrinksForCurrentWeek()
        val maxPossibleIntake =
            calculateMaxAlcoholIntakeForIntoxication(
                weight = profile.weight,
                isMale = profile.gender == Gender.MALE
            )
        val maxIntakeForServeIntoxication = calculateMaxAlcoholIntakeForIntoxication(
            AlcoholIntoxication.SERVE_INTOXICATION, profile.weight, profile.gender == Gender.MALE
        )
        val maxIntakeForMildIntoxication = calculateMaxAlcoholIntakeForIntoxication(
            AlcoholIntoxication.MILD_INTOXICATION, profile.weight, profile.gender == Gender.MALE
        )
        val lineConfiguration = LineConfiguration(
            safeIntoxicationLinePercent = maxIntakeForMildIntoxication / maxPossibleIntake,
            intoxicationLinePercent = (maxIntakeForServeIntoxication - maxIntakeForMildIntoxication) / maxPossibleIntake,
            hardIntoxicationLinePercent = (maxPossibleIntake - maxIntakeForServeIntoxication) / maxPossibleIntake
        )
        viewState = viewState.copy(
            helloLabel = getStringResource(Res.string.general_screen_hello_template, profile.name),
            weekStatisticData = viewState.weekStatisticData.copy(
                monday = updateDayStatistic(
                    viewState.weekStatisticData.monday,
                    maxPossibleIntake,
                    intakes,
                    profile,
                    lineConfiguration
                ),
                thursday = updateDayStatistic(
                    viewState.weekStatisticData.thursday,
                    maxPossibleIntake,
                    intakes,
                    profile,
                    lineConfiguration
                ),
                wednesday = updateDayStatistic(
                    viewState.weekStatisticData.wednesday,
                    maxPossibleIntake,
                    intakes,
                    profile,
                    lineConfiguration
                ),
                tuesday = updateDayStatistic(
                    viewState.weekStatisticData.tuesday,
                    maxPossibleIntake,
                    intakes,
                    profile,
                    lineConfiguration
                ),
                friday = updateDayStatistic(
                    viewState.weekStatisticData.friday,
                    maxPossibleIntake,
                    intakes,
                    profile,
                    lineConfiguration
                ),
                saturday = updateDayStatistic(
                    viewState.weekStatisticData.saturday,
                    maxPossibleIntake,
                    intakes,
                    profile,
                    lineConfiguration
                ),
                sunday = updateDayStatistic(
                    viewState.weekStatisticData.sunday,
                    maxPossibleIntake,
                    intakes,
                    profile,
                    lineConfiguration
                )
            )
        )
    }

    private fun updateDayStatistic(
        dayStatistic: DayStatistic,
        maxPossibleIntake: PureAlcoholGm,
        intakes: List<DrinkIntake>,
        user: User,
        lineConfiguration: LineConfiguration
    ): DayStatistic {
        return intakes
            .filter { it.date.dayOfWeek == dayStatistic.dayOfWeek }
            .let { filterIntakes ->
                val pureAlcoholIntake = filterIntakes.sumOf { calculatePureAlcoholIntake(it) }
                dayStatistic.copy(
                    statisticPercent = pureAlcoholIntake / maxPossibleIntake,
                    intoxicationStatus = getIntoxicationLevel(
                        pureAlcoholIntake, user.weight, user.gender == Gender.MALE
                    ),
                    lineConfiguration = lineConfiguration,
                )
            }
    }

    override fun processEvent(viewEvent: GeneralEvent) {
        when (viewEvent) {
            GeneralEvent.OnIntoxicationWarningClick -> {

            }

            GeneralEvent.OnLogDrinkClick -> viewAction = GeneralAction.OpenLogDrinkScreen
        }
    }
}