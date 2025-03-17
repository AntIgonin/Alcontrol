package com.smd.alcontrol.base.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel<State : Any, Action, Event : Any>(
    initialState: State,
    configureEventHandlersBuilder: EventHandlers.Config<Event>.() -> Unit = {},
) : ViewModel() {

    private val eventHandlers: EventHandlers<Event>

    init {
        val eventHandlersConfig = EventHandlers.Config<Event>()
        eventHandlersConfig.configureEventHandlersBuilder()
        eventHandlers = EventHandlers(
            config = eventHandlersConfig,
            coroutineScope = viewModelScope,
            processEvent = { processEvent(it) },
        )
    }

    private val _viewStates = MutableStateFlow(initialState)
    private val _viewActions =
        MutableSharedFlow<Action?>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    fun viewStates(): StateFlow<State> = _viewStates.asStateFlow()
    fun viewActions(): SharedFlow<Action?> = _viewActions.asSharedFlow()

    protected var viewState: State
        get() = _viewStates.value
        set(value) {
            _viewStates.value = value
        }

    protected var viewAction: Action?
        get() = _viewActions.replayCache.last()
        set(value) {
            value?.let { onBeforeViewAction(it) }
            _viewActions.tryEmit(value)
        }

    fun obtainEvent(viewEvent: Event) {
        eventHandlers.processEvent(viewEvent)
    }

    protected abstract fun processEvent(viewEvent: Event)

    protected open fun onBeforeViewAction(action: Action) {}

    fun clearAction() {
        viewAction = null
    }
}