package com.smd.alcontrol.features.logdrink

import alcontrol.composeapp.generated.resources.Res
import alcontrol.composeapp.generated.resources.general_bottom_sheet_select_drinks
import alcontrol.composeapp.generated.resources.general_bottom_sheet_select_volume
import androidx.lifecycle.viewModelScope
import com.smd.alcontrol.base.viewmodel.BaseViewModel
import com.smd.alcontrol.common.AlcValue
import com.smd.alcontrol.common.Drink
import com.smd.alcontrol.common.Type
import com.smd.alcontrol.database.model.DrinkIntake
import com.smd.alcontrol.database.repository.DrinkRepository
import com.smd.alcontrol.features.general.GeneralEvent
import com.smd.alcontrol.utils.date.formatDateForUser
import com.smd.alcontrol.utils.getStringResource
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime

class LogDrinkViewModel(
    private val drinkRepository: DrinkRepository
) : BaseViewModel<LogDrinkViewState, LogDrinkAction, LogDrinkEvent>(
    initialState = LogDrinkViewState(),
    configureEventHandlersBuilder = {
        listOf(
            LogDrinkEvent.ClickOnDrinkFromBottomSheet::class,
            LogDrinkEvent.InputDrinkVolume::class,
            LogDrinkEvent.AddDrinkVolumeFromChip::class
        ).immediate()
    }
) {

    init {
        viewState = viewState.copy(
            selectDrinkData = SelectDrinkData(getStringResource(Res.string.general_bottom_sheet_select_drinks))
        )
    }

    private fun addNewDrinkIntake(
        name: String,
        alcValue: AlcValue,
        type: Type,
        volume: Int,
        date: LocalDateTime
    ) =
        viewModelScope.launch {
            val drinkIntake = DrinkIntake(
                date = date,
                drink = Drink(name, alcValue, type),
                volumeMl = volume
            )
            drinkRepository.insertDrinkIntake(drinkIntake)
        }

    override fun processEvent(viewEvent: LogDrinkEvent) {
        when(viewEvent) {
            is LogDrinkEvent.ClickOnDrinkFromBottomSheet -> {
                viewState = viewState
                viewState = viewState.copy(
                    selectDrinkData = viewState.selectDrinkData?.copy(
                        selectedDrinks = viewState.selectDrinkData?.selectedDrinks
                            ?.toMutableList()
                            .apply {
                                if (!this?.remove(viewEvent.drink)!!) this.add(viewEvent.drink)
                            } ?: listOf()
                    )
                )
            }

            LogDrinkEvent.ClickBack -> {
                viewState = viewState.copy(
                    selectDrinkData = SelectDrinkData(
                        title = getStringResource(Res.string.general_bottom_sheet_select_drinks),
                        selectedDrinks = viewState.selectDrinkVolumeData?.drinks?.map { it.drink }
                            ?: listOf()
                    )
                )
                viewState = viewState.copy(
                    selectDrinkVolumeData = null
                )
            }

            LogDrinkEvent.ClickDone -> {
                viewState.selectDrinkVolumeData?.let { data ->
                    data.drinks.forEach {
                        addNewDrinkIntake(
                            it.drink.name,
                            it.drink.alcValue,
                            it.drink.type,
                            it.inputValue.toIntOrNull() ?: 0,
                            data.date
                        )
                    }
                }
                viewState = viewState.copy(
                    selectDrinkData = null,
                    selectDrinkVolumeData = null
                )
            }

            LogDrinkEvent.ClickNext -> {
                viewState = viewState.copy(
                    selectDrinkVolumeData = SelectDrinkVolumeData(
                        title = getStringResource(Res.string.general_bottom_sheet_select_volume),
                        drinks = viewState.selectDrinkData?.selectedDrinks?.map {
                            SelectDrinkVolumeData.DrinkWithValue(
                                it
                            )
                        } ?: listOf()
                    )
                )
                viewState = viewState.copy(
                    selectDrinkData = null
                )
            }

            is LogDrinkEvent.InputDrinkVolume -> {
                viewState = viewState.copy(
                    selectDrinkVolumeData = viewState.selectDrinkVolumeData?.copy(
                        drinks = viewState.selectDrinkVolumeData?.drinks?.map {
                            if (it.drink == viewEvent.drinkWithValue.drink) {
                                it.copy(inputValue = viewEvent.input)
                            } else {
                                it
                            }
                        } ?: listOf()
                    )
                )
            }

            is LogDrinkEvent.AddDrinkVolumeFromChip -> {
                viewState = viewState.copy(
                    selectDrinkVolumeData = viewState.selectDrinkVolumeData?.copy(
                        drinks = viewState.selectDrinkVolumeData?.drinks?.map { drink ->
                            if (drink.drink == viewEvent.drinkWithValue.drink) {
                                drink.copy(
                                    inputValue = (drink.inputValue.toIntOrNull()
                                        .let { it ?: 0 } + viewEvent.volume).toString())
                            } else {
                                drink
                            }
                        } ?: listOf()
                    )
                )
            }

            LogDrinkEvent.ClickOnSelectDate -> viewState = viewState.copy(
                selectDrinkVolumeData = viewState.selectDrinkVolumeData?.copy(
                    isDatePickerShow = true
                )
            )

            is LogDrinkEvent.SelectDate -> viewState = viewState.copy(
                selectDrinkVolumeData = viewState.selectDrinkVolumeData?.copy(
                    isDatePickerShow = false,
                    date = viewEvent.localDateTime,
                    dateText = formatDateForUser(viewEvent.localDateTime.date)
                )
            )

            LogDrinkEvent.ClickBackIcon -> viewAction = LogDrinkAction.GoToMainScreen
        }
    }
}