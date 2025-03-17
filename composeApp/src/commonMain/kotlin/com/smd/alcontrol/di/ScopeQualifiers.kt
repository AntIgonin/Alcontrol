package com.smd.alcontrol.di

import org.koin.core.module.KoinDslMarker
import org.koin.core.module.Module
import org.koin.dsl.ScopeDSL
import org.koin.mp.KoinPlatform.getKoin

class MainScopeQualifier

fun getMainScope() = getKoin().getOrCreateScope<MainScopeQualifier>("main_scope")

@KoinDslMarker
inline fun Module.mainScope(scopeSet: ScopeDSL.() -> Unit) {
    scope<MainScopeQualifier>(scopeSet)
}
