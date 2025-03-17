package com.smd.alcontrol.features.splash

class SplashViewState(
)

sealed class SplashAction {
    data object GoToMainScreen: SplashAction()
}

sealed class SplashEvent {

}