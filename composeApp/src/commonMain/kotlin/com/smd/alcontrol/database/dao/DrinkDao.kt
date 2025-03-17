package com.smd.alcontrol.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.smd.alcontrol.database.model.DrinkIntake

@Dao
interface DrinkDao {
    @Query("SELECT * FROM drink_intake")
    suspend fun getAllDrinkIntakes(): List<DrinkIntake>

    @Query("SELECT * FROM drink_intake WHERE date BETWEEN :start AND :end")
    suspend fun getDrinksByDate(start: String, end: String): List<DrinkIntake>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDrinkIntake(drinkIntake: DrinkIntake)

    @Delete
    suspend fun deleteDrinkIntake(drinkIntake: DrinkIntake)

    @Query("DELETE FROM drink_intake WHERE id = :id")
    suspend fun deleteById(id: Long)
}
