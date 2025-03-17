package com.smd.alcontrol.features.splash

import androidx.lifecycle.viewModelScope
import com.smd.alcontrol.base.viewmodel.BaseViewModel
import com.smd.alcontrol.database.model.Gender
import com.smd.alcontrol.database.model.User
import com.smd.alcontrol.database.repository.UserRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.isoDayNumber

class SplashViewModel(
    val userRepository: UserRepository
) : BaseViewModel<SplashViewState, SplashAction, SplashEvent>(
    initialState = SplashViewState()
) {

    init {
        viewModelScope.launch {

            viewAction = SplashAction.GoToMainScreen
        }
    }

    override fun processEvent(viewEvent: SplashEvent) {
        // empty events
    }
}