package com.smd.alcontrol.features.onboarding

data class OnboardingViewState(
    val pages: List<String>
)

sealed class OnboardingAction {

}

sealed class OnboardingViewEvent {

}