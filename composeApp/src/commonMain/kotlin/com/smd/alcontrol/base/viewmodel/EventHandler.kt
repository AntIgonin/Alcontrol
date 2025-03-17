package com.smd.alcontrol.base.viewmodel


import com.smd.alcontrol.utils.DefaultThrottleTimingComparison
import com.smd.alcontrol.utils.ThrottleTimingComparison
import com.smd.alcontrol.utils.debounceActionWithParam
import com.smd.alcontrol.utils.throttleActionWithParam
import kotlinx.coroutines.CoroutineScope
import kotlin.reflect.KClass

internal var eventHandleThrottleTimingComparison: ThrottleTimingComparison =
    DefaultThrottleTimingComparison()

internal typealias EventHandler<T> = (T) -> Unit

sealed interface EventHandlerType {
    data object Immediate : EventHandlerType
    data class Throttle(val throttleTimeMs: Long) : EventHandlerType
    data class Debounce(val debounceTimeMs: Long = 500) : EventHandlerType
}

class EventHandlers<Event : Any>(
    val config: Config<Event>,
    val coroutineScope: CoroutineScope,
    val processEvent: (Event) -> Unit,
) {

    private val defaultHandlerType: EventHandlerType = config.defaultEventHandlerType
    private val eventHandlersMap: MutableMap<KClass<out Event>, EventHandler<Event>> =
        config.eventHandlersTypesMap.mapValues {
            buildHandler(
                it.value,
                coroutineScope,
                processEvent
            )
        }.toMutableMap()

    fun processEvent(viewEvent: Event) {
        provideEventHandler(viewEvent)(viewEvent)
    }

    private fun provideEventHandler(viewEvent: Event): EventHandler<Event> {
        var handler = eventHandlersMap[viewEvent::class]
        if (handler == null) {
            handler = buildHandler(defaultHandlerType, coroutineScope, processEvent)
            eventHandlersMap[viewEvent::class] = handler
        }
        return handler
    }

    class Config<Event : Any> {
        private var _defaultEventHandlerType: EventHandlerType =
            EventHandlerType.Throttle(throttleTimeMs = DEFAULT_THROTTLE_TIME_MS)

        val defaultEventHandlerType
            get() = _defaultEventHandlerType

        private val _eventHandlersTypesMap: MutableMap<KClass<out Event>, EventHandlerType> =
            mutableMapOf()

        val eventHandlersTypesMap: Map<KClass<out Event>, EventHandlerType> = _eventHandlersTypesMap

        fun addEventHandlerType(eventToHandlerType: Pair<KClass<out Event>, EventHandlerType>) {
            _eventHandlersTypesMap[eventToHandlerType.first] = eventToHandlerType.second
        }

        fun setImmediateDefault() {
            _defaultEventHandlerType = EventHandlerType.Immediate
        }

        fun setThrottleDefault(throttleTimeMs: Long = DEFAULT_THROTTLE_TIME_MS) {
            _defaultEventHandlerType = EventHandlerType.Throttle(throttleTimeMs)
        }

        fun setDebounceDefault(debounceTimeMs: Long = DEFAULT_DEBOUNCE_TIME_MS) {
            _defaultEventHandlerType = EventHandlerType.Debounce(debounceTimeMs)
        }

        fun KClass<out Event>.immediate() {
            addEventHandlerType(this to EventHandlerType.Immediate)
        }

        fun List<KClass<out Event>>.immediate() {
            addEventHandlerType(this, EventHandlerType.Immediate)
        }

        fun KClass<out Event>.throttle(throttleTimeMs: Long = DEFAULT_THROTTLE_TIME_MS) {
            addEventHandlerType(this to EventHandlerType.Throttle(throttleTimeMs))
        }

        fun List<KClass<out Event>>.throttle(throttleTimeMs: Long = DEFAULT_THROTTLE_TIME_MS) {
            addEventHandlerType(this, EventHandlerType.Throttle(throttleTimeMs))
        }

        fun KClass<out Event>.debounce(debounceTimeMs: Long = DEFAULT_DEBOUNCE_TIME_MS) {
            addEventHandlerType(this to EventHandlerType.Debounce(debounceTimeMs))
        }

        fun List<KClass<out Event>>.debounce(debounceTimeMs: Long = DEFAULT_DEBOUNCE_TIME_MS) {
            addEventHandlerType(this, EventHandlerType.Debounce(debounceTimeMs))
        }

        private fun addEventHandlerType(
            eventTypes: List<KClass<out Event>>,
            eventHandlerType: EventHandlerType,
        ) {
            eventTypes.forEach { addEventHandlerType(it to eventHandlerType) }
        }

    }

    companion object {
        const val DEFAULT_DEBOUNCE_TIME_MS = 500L
        const val DEFAULT_THROTTLE_TIME_MS = 500L

        private fun <Event : Any> buildHandler(
            type: EventHandlerType,
            coroutineScope: CoroutineScope,
            processEvent: (Event) -> Unit,
        ): EventHandler<Event> {
            val action: (Event) -> Unit = { processEvent(it) }
            val handler: EventHandler<Event> = when (type) {
                EventHandlerType.Immediate -> action
                is EventHandlerType.Throttle ->
                    throttleActionWithParam(
                        throttleTimeMs = type.throttleTimeMs,
                        throttleTimingComparison = eventHandleThrottleTimingComparison,
                        action = action,
                    )

                is EventHandlerType.Debounce -> {
                    val debouncedAction = debounceActionWithParam(
                        debounceTimeMs = type.debounceTimeMs,
                        scope = coroutineScope,
                        action = action,
                    )
                    val h: (Event) -> Unit = { e: Event -> debouncedAction(e) }
                    h
                }
            }
            return handler
        }
    }
}