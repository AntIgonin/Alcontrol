package com.smd.alcontrol.di

import com.smd.alcontrol.database.AppDatabase
import com.smd.alcontrol.database.repository.DrinkRepository
import com.smd.alcontrol.database.repository.DrinkRepositoryImpl
import com.smd.alcontrol.database.repository.UserRepository
import com.smd.alcontrol.database.repository.UserRepositoryImpl
import com.smd.alcontrol.features.calendar.CalendarViewModel
import com.smd.alcontrol.features.general.GeneralViewModel
import com.smd.alcontrol.features.logdrink.LogDrinkViewModel
import com.smd.alcontrol.features.profile.ProfileViewModel
import com.smd.alcontrol.features.splash.SplashViewModel
import org.koin.dsl.module

internal val mainModule
    get() = module {
        mainScope {
            scoped<DrinkRepository> { DrinkRepositoryImpl(get<AppDatabase>().drinkDao()) }
            scoped<UserRepository> { UserRepositoryImpl(get<AppDatabase>().userDao()) }

            factory { SplashViewModel(get()) }
            factory { GeneralViewModel(get(), get()) }
            factory { CalendarViewModel(get(), get()) }
            factory { ProfileViewModel(get()) }
            factory { LogDrinkViewModel(get()) }
        }
    }