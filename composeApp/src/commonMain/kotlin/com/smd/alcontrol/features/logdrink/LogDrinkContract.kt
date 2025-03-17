package com.smd.alcontrol.features.logdrink

import alcontrol.composeapp.generated.resources.Res
import alcontrol.composeapp.generated.resources.drink_beer_category
import alcontrol.composeapp.generated.resources.drink_cocktails_category
import alcontrol.composeapp.generated.resources.drink_spirits_category
import alcontrol.composeapp.generated.resources.drink_wine_category
import com.smd.alcontrol.common.beers
import com.smd.alcontrol.common.cocktails
import com.smd.alcontrol.common.defaultSpirits
import com.smd.alcontrol.common.model.DrinkUI
import com.smd.alcontrol.common.model.toUI
import com.smd.alcontrol.common.wines
import com.smd.alcontrol.utils.date.formatDateForUser
import com.smd.alcontrol.utils.getStringResource
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class LogDrinkViewState(
    val selectDrinkData: SelectDrinkData? = null,
    val selectDrinkVolumeData: SelectDrinkVolumeData? = null
)

data class SelectDrinkData(
    val title: String,
    val drinkRowDataList: List<DrinkRowData> = listOf(
        DrinkRowData(
            getStringResource(Res.string.drink_spirits_category),
            defaultSpirits().map { it.toUI() }),
        DrinkRowData(getStringResource(Res.string.drink_wine_category), wines().map { it.toUI() }),
        DrinkRowData(getStringResource(Res.string.drink_beer_category), beers().map { it.toUI() }),
        DrinkRowData(
            getStringResource(Res.string.drink_cocktails_category),
            cocktails().map { it.toUI() })
    ),
    val selectedDrinks: List<DrinkUI> = listOf()
) {
    data class DrinkRowData(val categoryLabel: String, val drinkList: List<DrinkUI>)
}

data class SelectDrinkVolumeData(
    val title: String,
    val drinks: List<DrinkWithValue> = listOf(),
    val date: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
    val dateText: String = formatDateForUser(date.date),
    val isDatePickerShow: Boolean = false
) {
    data class DrinkWithValue(val drink: DrinkUI, val inputValue: String = "")
}

sealed class LogDrinkAction {
    data object GoToMainScreen: LogDrinkAction()
}

sealed class LogDrinkEvent {
    data object ClickBackIcon: LogDrinkEvent()
    data class ClickOnDrinkFromBottomSheet(val drink: DrinkUI) : LogDrinkEvent()
    data class InputDrinkVolume(
        val drinkWithValue: SelectDrinkVolumeData.DrinkWithValue,
        val input: String
    ) : LogDrinkEvent()

    data class AddDrinkVolumeFromChip(
        val drinkWithValue: SelectDrinkVolumeData.DrinkWithValue,
        val volume: Int
    ) : LogDrinkEvent()

    data object ClickNext : LogDrinkEvent()
    data object ClickBack : LogDrinkEvent()
    data object ClickDone : LogDrinkEvent()
    data object ClickOnSelectDate : LogDrinkEvent()
    data class SelectDate(val localDateTime: LocalDateTime): LogDrinkEvent()
}