package com.smd.alcontrol.database.model

import alcontrol.composeapp.generated.resources.Res
import alcontrol.composeapp.generated.resources.female
import alcontrol.composeapp.generated.resources.male
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.smd.alcontrol.utils.alcohol.PureAlcoholGm
import com.smd.alcontrol.utils.getStringResource
import kotlinx.datetime.DayOfWeek

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val age: Int,
    val weight: Double,
    val gender: Gender,
    val avgPureAlcoholConsumption: PureAlcoholGm,
    val daysOfConsumption: List<DayOfWeek>
)

enum class Gender(val displayName: String) {
    MALE(getStringResource(Res.string.male)),
    FEMALE(getStringResource(Res.string.female))
}