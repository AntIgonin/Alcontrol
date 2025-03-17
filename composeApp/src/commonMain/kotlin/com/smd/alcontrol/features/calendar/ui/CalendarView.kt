package com.smd.alcontrol.features.calendar.ui

import alcontrol.composeapp.generated.resources.Res
import alcontrol.composeapp.generated.resources.calendar_your_statistic
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.smd.alcontrol.common.AlcoholIntoxication
import com.smd.alcontrol.common.model.DrinkIntakeUI
import com.smd.alcontrol.features.calendar.CalendarViewEvent
import com.smd.alcontrol.features.calendar.CalendarViewState
import com.smd.alcontrol.utils.color.mapAlcoholIntoxicationToColor
import com.smd.alcontrol.utils.date.daysInMonth
import com.smd.alcontrol.utils.date.formatDateForUser
import com.smd.alcontrol.utils.date.now
import com.smd.alcontrol.utils.getStringResource
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate


@Composable
internal fun CalendarView(
    viewState: CalendarViewState,
    obtainEvent: (CalendarViewEvent) -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
    ) {
        MonthSwitcher(
            currentDate = viewState.currentDate,
            onPreviousMonth = {
                obtainEvent(CalendarViewEvent.OnPreviousMonth)
            },
            onNextMonth = {
                obtainEvent(CalendarViewEvent.OnNextMonth)
            }
        )
        CalendarGrid(
            currentDate = viewState.currentDate,
            selectedDay = viewState.selectedDay,
            dayToStatus = viewState.dayToStatus
        ) {
            obtainEvent(CalendarViewEvent.SelectDay(it))
        }
        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
        Spacer(modifier = Modifier.height(16.dp))
        DayInformation(
            viewState.selectedDay, viewState.displayedIntakes, obtainEvent
        )
    }
}

@Composable
fun MonthSwitcher(
    currentDate: LocalDate,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onPreviousMonth) {
            Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Previous Month")
        }

        Text(
            text = currentDate.month.name,
            style = MaterialTheme.typography.headlineMedium
        )

        IconButton(onClick = onNextMonth) {
            Icon(Icons.AutoMirrored.Default.ArrowForward, contentDescription = "Next Month")
        }
    }
}

@Composable
fun CalendarGrid(
    currentDate: LocalDate,
    selectedDay: LocalDate,
    dayToStatus: Map<Int, AlcoholIntoxication>,
    selectNewDay: (LocalDate) -> Unit,
) {
    val daysOfWeek = DayOfWeek.entries.map { it.name.take(3) }
    val firstDayOfMonth = LocalDate(currentDate.year, currentDate.month, 1)
    val daysInMonth = firstDayOfMonth.month.daysInMonth(firstDayOfMonth.year)
    val startDayOfWeek = firstDayOfMonth.dayOfWeek.ordinal

    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            daysOfWeek.forEach { day ->
                Text(text = day, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
            }
        }

        Column(modifier = Modifier.fillMaxWidth()) {
            var dayCounter = 1
            for (row in 0 until ((startDayOfWeek + daysInMonth + 6) / 7)) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    for (col in 0 until 7) {
                        if (row == 0 && col < startDayOfWeek || dayCounter > daysInMonth) {
                            Spacer(modifier = Modifier.size(40.dp).padding(8.dp).weight(1f))
                        } else {
                            val day = dayCounter
                            CalendarDay(
                                Modifier.weight(1f).padding(8.dp),
                                dayCounter,
                                dayToStatus[dayCounter],
                                isToday = currentDate.month == now().month && dayCounter == now().dayOfMonth,
                                isSelected = currentDate.month == selectedDay.month && dayCounter == selectedDay.dayOfMonth
                            ) {
                                selectNewDay(
                                    LocalDate(
                                        currentDate.year,
                                        currentDate.month,
                                        day
                                    )
                                )
                            }
                            dayCounter++
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CalendarDay(
    modifier: Modifier,
    day: Int,
    status: AlcoholIntoxication?,
    isToday: Boolean,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier.size(40.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .clickable { onClick() }
                .border(
                    if (isSelected) BorderStroke(
                        1.dp,
                        MaterialTheme.colorScheme.onTertiaryContainer
                    ) else BorderStroke(0.dp, Color.Transparent), CircleShape
                )
                .background(if (isToday) MaterialTheme.colorScheme.tertiaryContainer else Color.Transparent),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$day",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )
        }
        if (status != null) {
            Box(modifier.size(4.dp).clip(CircleShape).background(
                mapAlcoholIntoxicationToColor(status)
            ))
        }
    }
}

@Composable
fun DayInformation(day: LocalDate, drinkIntakes: List<DrinkIntakeUI>, obtainEvent: (CalendarViewEvent) -> Unit) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                getStringResource(Res.string.calendar_your_statistic),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                formatDateForUser(day),
                style = MaterialTheme.typography.titleMedium
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            drinkIntakes.forEach {
                DrinkIntakeCard(it) { id ->
                    obtainEvent(CalendarViewEvent.DeleteIntake(id))
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}