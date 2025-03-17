package com.smd.alcontrol.features.general.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.smd.alcontrol.common.AlcoholIntoxication
import com.smd.alcontrol.features.general.DrinksInformationData
import com.smd.alcontrol.features.general.GeneralEvent
import com.smd.alcontrol.features.general.GeneralViewState
import com.smd.alcontrol.features.general.WeekStatisticData
import com.smd.alcontrol.theme.danger_alc_status
import com.smd.alcontrol.theme.normal_alc_status
import com.smd.alcontrol.theme.safe_alc_status
import com.smd.alcontrol.utils.color.mapAlcoholIntoxicationToColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun GeneralView(
    viewState: GeneralViewState,
    obtainEvent: (GeneralEvent) -> Unit
) {
    Column(
        modifier = Modifier.padding(horizontal = 24.dp).fillMaxSize().verticalScroll(
            rememberScrollState()
        )
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            viewState.helloLabel,
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            viewState.statisticLabel,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(16.dp))
        WeeklyStatisticView(viewState.weekStatisticData, obtainEvent)
        Spacer(modifier = Modifier.height(16.dp))
        DrinksInformationView(
            viewState.daysWithoutAlcoholLabel,
            viewState.drinksInformationData
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            obtainEvent(GeneralEvent.OnLogDrinkClick)
        }, modifier = Modifier.fillMaxWidth()) {
            Text(
                viewState.logAlcoholButtonText,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
internal fun WeeklyStatisticView(weekStatisticData: WeekStatisticData, obtainEvent: (GeneralEvent) -> Unit) {
    ElevatedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier.height(220.dp).fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(horizontal = 8.dp)) {
            weekStatisticData.days().forEach {
                Column(
                    modifier = Modifier.weight(1f).fillMaxHeight(),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .padding(start = 6.dp, end = 6.dp, bottom = 8.dp, top = 16.dp)
                            .weight(1f)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        it.lineConfiguration?.let { configuration ->
                            IntoxicationLevelLines(
                                safe = configuration.safeIntoxicationLinePercent.toFloat(),
                                normal = configuration.intoxicationLinePercent.toFloat(),
                                danger = configuration.hardIntoxicationLinePercent.toFloat()
                            )
                        }

                        Box(
                            modifier = Modifier
                                .animateContentSize()
                                .fillMaxHeight(it.statisticPercent.toFloat())
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter)
                                .clip(RoundedCornerShape(8.dp))
                                .background(mapAlcoholIntoxicationToColor(it.intoxicationStatus))
                        ) {
                            if (it.intoxicationStatus == AlcoholIntoxication.HARD_INTOXICATION
                                || it.intoxicationStatus == AlcoholIntoxication.RISK_OF_DEATH
                            ) {
                                Icon(
                                    Icons.Default.Warning,
                                    null,
                                    modifier = Modifier.align(Alignment.TopCenter).padding(8.dp).clickable {

                                    }
                                )
                            }

                        }
                    }

                    Spacer(modifier = Modifier.height(2.dp))
                    if (it.isCurrentDay) {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.secondary)
                        ) {
                            Text(
                                it.label,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSecondary,
                                modifier = Modifier.align(Alignment.Center)
                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                    } else {
                        Text(
                            it.label,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSecondary,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                }
            }
        }
    }
}

@Composable
fun IntoxicationLevelLines(
    safe: Float,
    normal: Float,
    danger: Float,
    color1: Color = safe_alc_status,
    color2: Color = normal_alc_status,
    color3: Color = danger_alc_status,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier.fillMaxWidth().weight(danger).background(color3.copy(alpha = 0.3f))
        ) // Верхний цвет
        Box(
            modifier = Modifier.fillMaxWidth().weight(normal).background(color2.copy(alpha = 0.3f))
        ) // Средний цвет
        Box(
            modifier = Modifier.fillMaxWidth().weight(safe).background(color1.copy(alpha = 0.3f))
        ) // Нижний цвет
    }
}


@Composable
internal fun DrinksInformationView(
    daysWithoutAlcoholLabel: String,
    drinksInformationData: DrinksInformationData
) {
    ElevatedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier.height(100.dp).fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .fillMaxWidth(0.2f)
                    .background(MaterialTheme.colorScheme.secondary),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    drinksInformationData.daysWithoutAlcohol.toString(),
                    color = MaterialTheme.colorScheme.onSecondary,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(8.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                daysWithoutAlcoholLabel,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )
        }
    }
}