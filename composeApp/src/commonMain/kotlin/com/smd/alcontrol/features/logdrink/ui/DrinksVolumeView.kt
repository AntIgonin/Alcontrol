package com.smd.alcontrol.features.logdrink.ui

import alcontrol.composeapp.generated.resources.Res
import alcontrol.composeapp.generated.resources.back
import alcontrol.composeapp.generated.resources.done
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.smd.alcontrol.common.view.CustomDatePickerDialog
import com.smd.alcontrol.features.logdrink.LogDrinkEvent
import com.smd.alcontrol.features.logdrink.SelectDrinkVolumeData
import org.jetbrains.compose.resources.stringResource

@Composable
fun DrinksVolumeView(
    selectDrinkVolumeData: SelectDrinkVolumeData,
    obtainEvent: (LogDrinkEvent) -> Unit
) {
    val drinkWithValues = selectDrinkVolumeData.drinks
    Box {
        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    selectDrinkVolumeData.title,
                    style = MaterialTheme.typography.headlineMedium
                )
                Row {
                    Text(
                        selectDrinkVolumeData.dateText,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.clickable {
                            obtainEvent(LogDrinkEvent.ClickOnSelectDate)
                        }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.Filled.DateRange, null, modifier = Modifier.clickable {
                        obtainEvent(LogDrinkEvent.ClickOnSelectDate)
                    })
                }
            }
            drinkWithValues.forEach {
                DrinkVolumeCard(it, obtainEvent)
            }
            Spacer(modifier = Modifier.height(64.dp))
        }
        Row(modifier = Modifier.align(Alignment.BottomCenter)) {
            Button(
                onClick = {
                    obtainEvent(LogDrinkEvent.ClickBack)
                },
                modifier = Modifier.fillMaxWidth(0.3f)
            ) {
                Text(
                    stringResource(Res.string.back), style = MaterialTheme.typography.titleMedium
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = {
                    obtainEvent(LogDrinkEvent.ClickDone)
                },
                modifier = Modifier.fillMaxWidth(0.3f)
            ) {
                Text(
                    stringResource(Res.string.done), style = MaterialTheme.typography.titleMedium
                )
            }
        }
        if (selectDrinkVolumeData.isDatePickerShow) {
            CustomDatePickerDialog(onDateSelected = {
                obtainEvent(LogDrinkEvent.SelectDate(it))
            }, onDismiss = {

            })
        }
    }
}

@Composable
private fun DrinkVolumeCard(
    drinkWithValue: SelectDrinkVolumeData.DrinkWithValue,
    obtainEvent: (LogDrinkEvent) -> Unit
) {
    OutlinedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround

        ) {
            Box(
                modifier = Modifier.weight(1f).fillMaxHeight(),
                contentAlignment = Alignment.TopStart
            ) {
                Text(
                    "${drinkWithValue.drink.name} ${drinkWithValue.drink.alcohol}",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            Column(
                modifier = Modifier.fillMaxHeight().weight(1f),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                AssistChip(
                    onClick = {
                        obtainEvent(LogDrinkEvent.AddDrinkVolumeFromChip(drinkWithValue, 50))
                    },
                    label = { Text("50 ml") },
                    leadingIcon = { Icon(Icons.Default.Add, null) }
                )
                AssistChip(
                    onClick = {
                        obtainEvent(LogDrinkEvent.AddDrinkVolumeFromChip(drinkWithValue, 100))
                    },
                    label = { Text("100 ml") },
                    leadingIcon = { Icon(Icons.Default.Add, null) }
                )
            }
            OutlinedTextField(
                drinkWithValue.inputValue,
                onValueChange = { stringValue ->
                    obtainEvent(
                        LogDrinkEvent.InputDrinkVolume(
                            drinkWithValue,
                            stringValue
                        )
                    )
                },
                label = { Text("How much?") },
                suffix = { Text("ml") },
                modifier = Modifier.weight(1f),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
        }
    }
}