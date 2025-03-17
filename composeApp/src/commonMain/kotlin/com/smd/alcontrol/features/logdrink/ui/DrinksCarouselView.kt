package com.smd.alcontrol.features.logdrink.ui

import alcontrol.composeapp.generated.resources.Res
import alcontrol.composeapp.generated.resources.ic_chek_green
import alcontrol.composeapp.generated.resources.next
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.smd.alcontrol.common.model.DrinkUI
import com.smd.alcontrol.features.logdrink.LogDrinkEvent
import com.smd.alcontrol.features.logdrink.SelectDrinkData
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
internal fun DrinksCarousel(
    selectDrinkData: SelectDrinkData,
    obtainEvent: (LogDrinkEvent) -> Unit
) {
    Box(modifier = Modifier.verticalScroll(rememberScrollState()).fillMaxSize()) {
        Column {
            Spacer(modifier = Modifier.height(16.dp))
            Icon(Icons.AutoMirrored.Default.ArrowBack, null, modifier = Modifier.padding(start = 16.dp).clickable {
                obtainEvent(LogDrinkEvent.ClickBackIcon)
            })
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                selectDrinkData.title,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            selectDrinkData.drinkRowDataList.forEach { row ->
                DrinksRow(
                    row.categoryLabel,
                    row.drinkList,
                    selectDrinkData.selectedDrinks
                ) { drink ->
                    obtainEvent(LogDrinkEvent.ClickOnDrinkFromBottomSheet(drink))
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
            Spacer(modifier = Modifier.height(64.dp))
        }
        Button(
            onClick = {
                obtainEvent(LogDrinkEvent.ClickNext)
            },
            enabled = selectDrinkData.selectedDrinks.isNotEmpty(),
            modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth(0.5f)
        ) {
            Text(
                stringResource(Res.string.next), style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
private fun DrinksRow(
    title: String,
    drinks: List<DrinkUI>,
    checkedDrinks: List<DrinkUI>,
    onClick: (DrinkUI) -> Unit
) {
    Text(
        title,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(start = 16.dp)
    )
    Spacer(modifier = Modifier.height(16.dp))
    Row(
        modifier = Modifier.fillMaxWidth().padding(start = 8.dp).horizontalScroll(
            rememberScrollState()
        )
    ) {
        drinks.forEach {
            ElevatedCard(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                ),
                modifier = Modifier.padding(horizontal = 8.dp),
                onClick = {
                    onClick(it)
                }
            ) {
                Box(
                    modifier = Modifier
                        .height(140.dp)
                        .wrapContentWidth()
                ) {
                    Row {
                        Column(
                            modifier = Modifier.width(110.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Row(modifier = Modifier.fillMaxWidth()) {
                                if (checkedDrinks.contains(it)) {
                                    Image(
                                        vectorResource(Res.drawable.ic_chek_green), null,
                                        modifier = Modifier.padding(start = 8.dp, top = 8.dp)
                                    )
                                }
                                Box(modifier = Modifier.fillMaxWidth()) {
                                    Text(
                                        text = it.alcohol,
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier.padding(8.dp).align(Alignment.CenterEnd)
                                    )
                                }

                            }
                            Image(
                                vectorResource(it.iconRes),
                                null,
                                modifier = Modifier.size(50.dp)
                            )
                            Text(
                                text = it.name,
                                style = MaterialTheme.typography.titleSmall,
                                modifier = Modifier.padding(8.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}