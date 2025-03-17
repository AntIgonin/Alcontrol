package com.smd.alcontrol

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.smd.alcontrol.database.AppDatabase
import com.smd.alcontrol.database.dbFileName
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.Module
import org.koin.dsl.module

internal fun platformModule(context: Application): Module = module {
    single<AppDatabase> {
        val dbFile = context.getDatabasePath(dbFileName)
        Room.databaseBuilder<AppDatabase>(
            context = context,
            name = dbFile.absolutePath,
        ).setDriver(BundledSQLiteDriver()).setQueryCoroutineContext(Dispatchers.IO).build()
    }
}