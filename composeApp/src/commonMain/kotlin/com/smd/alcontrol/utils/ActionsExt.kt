package com.smd.alcontrol.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

internal fun debounceAction(
    debounceTimeMs: Long,
    scope: CoroutineScope,
    action: () -> Unit
): () -> Job {
    var debounceJob: Job? = null

    return {
        debounceJob?.cancel()
        val newDebounceJob = scope.launch {
            delay(debounceTimeMs)
            action()
        }
        debounceJob = newDebounceJob
        newDebounceJob
    }
}

internal fun <T> debounceActionWithParam(
    debounceTimeMs: Long,
    scope: CoroutineScope,
    action: (T) -> Unit
): (T) -> Job {
    var debounceJob: Job? = null

    return { param: T ->
        debounceJob?.cancel()
        val newDebounceJob = scope.launch {
            delay(debounceTimeMs)
            action(param)
        }
        debounceJob = newDebounceJob
        newDebounceJob
    }
}


internal fun <T> throttleActionWithParam(
    throttleTimeMs: Long,
    throttleTimingComparison: ThrottleTimingComparison = DefaultThrottleTimingComparison(),
    action: (T) -> Unit,
): (T) -> Unit {
    var lastTimeEventHandledMs: Long = 0
    return { value: T ->
        if (throttleTimingComparison.isTimeMoreThan(lastTimeEventHandledMs + throttleTimeMs)) {
            lastTimeEventHandledMs = Clock.System.now().toEpochMilliseconds()
            action(value)
        }
    }
}

interface ThrottleTimingComparison {
    fun isTimeMoreThan(ms: Long): Boolean
}

class DefaultThrottleTimingComparison : ThrottleTimingComparison {
    override fun isTimeMoreThan(ms: Long): Boolean =
        Clock.System.now().toEpochMilliseconds() > ms
}