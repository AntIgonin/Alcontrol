package com.smd.alcontrol.features.onboarding

import com.smd.alcontrol.base.viewmodel.BaseViewModel

class OnboardingViewModel : BaseViewModel<OnboardingViewState, OnboardingAction, OnboardingViewEvent>(
    initialState = OnboardingViewState(listOf())
) {
    override fun processEvent(viewEvent: OnboardingViewEvent) {
        TODO("Not yet implemented")
    }
}