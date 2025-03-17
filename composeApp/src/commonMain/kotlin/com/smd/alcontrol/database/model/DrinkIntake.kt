package com.smd.alcontrol.database.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.smd.alcontrol.common.Drink
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "drink_intake")
data class DrinkIntake(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: LocalDateTime,
    @Embedded val drink: Drink,
    val volumeMl: Int
)