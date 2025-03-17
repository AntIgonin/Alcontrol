package com.smd.alcontrol.features.general

import alcontrol.composeapp.generated.resources.Res
import alcontrol.composeapp.generated.resources.drink_beer_category
import alcontrol.composeapp.generated.resources.drink_cocktails_category
import alcontrol.composeapp.generated.resources.drink_spirits_category
import alcontrol.composeapp.generated.resources.drink_wine_category
import alcontrol.composeapp.generated.resources.general_bottom_sheet_select_drinks
import alcontrol.composeapp.generated.resources.general_bottom_sheet_select_volume
import alcontrol.composeapp.generated.resources.general_screen_alcohol_free_days
import alcontrol.composeapp.generated.resources.general_screen_current_plan
import alcontrol.composeapp.generated.resources.general_screen_favorite_drink
import alcontrol.composeapp.generated.resources.general_screen_hello_template
import alcontrol.composeapp.generated.resources.general_screen_log_last_drink
import alcontrol.composeapp.generated.resources.general_screen_statistic
import com.smd.alcontrol.common.AlcoholIntoxication
import com.smd.alcontrol.common.Drink
import com.smd.alcontrol.common.beers
import com.smd.alcontrol.common.cocktails
import com.smd.alcontrol.common.defaultSpirits
import com.smd.alcontrol.common.model.DrinkUI
import com.smd.alcontrol.common.model.toUI
import com.smd.alcontrol.common.vodka
import com.smd.alcontrol.common.wines
import com.smd.alcontrol.utils.alcohol.PureAlcoholGm
import com.smd.alcontrol.utils.date.formatDateForUser
import com.smd.alcontrol.utils.getStringResource
import kotlinx.datetime.Clock
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class GeneralViewState(
    val isLoading: Boolean,
    val helloLabel: String,
    val statisticLabel: String = getStringResource(Res.string.general_screen_statistic),
    val favoriteDrinkLabel: String = getStringResource(Res.string.general_screen_favorite_drink),
    val daysWithoutAlcoholLabel: String = getStringResource(Res.string.general_screen_alcohol_free_days),
    val weekStatisticData: WeekStatisticData = WeekStatisticData(),
    val drinksInformationData: DrinksInformationData = DrinksInformationData(0),
    val logAlcoholButtonText: String = getStringResource(Res.string.general_screen_log_last_drink),
)

data class WeekStatisticData(
    val monday: DayStatistic = DayStatistic("Пн", DayOfWeek.MONDAY),
    val tuesday: DayStatistic = DayStatistic("Вт", DayOfWeek.TUESDAY),
    val wednesday: DayStatistic = DayStatistic("Ср", DayOfWeek.WEDNESDAY),
    val thursday: DayStatistic = DayStatistic("Чт", DayOfWeek.THURSDAY),
    val friday: DayStatistic = DayStatistic("Пт", DayOfWeek.FRIDAY),
    val saturday: DayStatistic = DayStatistic("Сб", DayOfWeek.SATURDAY),
    val sunday: DayStatistic = DayStatistic("Вс", DayOfWeek.SUNDAY)
) {
    fun days() = listOf(monday, tuesday, wednesday, thursday, friday, saturday, sunday)
}

data class DayStatistic(
    val label: String,
    val dayOfWeek: DayOfWeek,
    val statisticPercent: Double = 0.0,
    val intoxicationStatus: AlcoholIntoxication = AlcoholIntoxication.NONE,
    val isCurrentDay: Boolean = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault()).dayOfWeek == dayOfWeek,
    val lineConfiguration: LineConfiguration? = null
)

data class LineConfiguration(
    val safeIntoxicationLinePercent: Double = 0.0,
    val intoxicationLinePercent: Double = 0.0,
    val hardIntoxicationLinePercent: Double = 0.0
)

data class DrinksInformationData(
    val daysWithoutAlcohol: Int
)


sealed class GeneralAction {
    data object OpenLogDrinkScreen: GeneralAction()
}

sealed class GeneralEvent {
    data object OnLogDrinkClick: GeneralEvent()
    data object OnIntoxicationWarningClick: GeneralEvent()
}