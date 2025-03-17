package com.smd.alcontrol.utils

import kotlinx.coroutines.runBlocking
import org.jetbrains.compose.resources.PluralStringResource
import org.jetbrains.compose.resources.StringResource

fun getStringResource(resource: StringResource): String =
    getStringResourceInternal(resource)

fun getStringResource(resource: StringResource, vararg args: Any): String =
    getStringResourceWithArgsInternal(resource, args)

fun getQuantityString(resource: PluralStringResource, quantity: Int): String =
    getPluralStringResourceWithQuantityInternal(resource, quantity)


internal val getStringResourceDefault: (resource: StringResource) -> String = { resource ->
    runBlocking { org.jetbrains.compose.resources.getString(resource) }
}

internal var getStringResourceInternal: (resource: StringResource) -> String = getStringResourceDefault


internal val getStringResourceWithArgsDefault: (resource: StringResource, args: Array<out Any>) -> String =
    { resource, args -> runBlocking { org.jetbrains.compose.resources.getString(resource, *args) } }

internal var getStringResourceWithArgsInternal: (resource: StringResource, args: Array<out Any>) -> String = getStringResourceWithArgsDefault


internal val getPluralStringResourceWithQuantityDefault: (resource: PluralStringResource, quantity: Int) -> String =
    { resource, quantity -> runBlocking { org.jetbrains.compose.resources.getPluralString(resource, quantity, quantity) } }

internal val getPluralStringResourceWithQuantityInternal: (resource: PluralStringResource, quantity: Int) -> String = getPluralStringResourceWithQuantityDefault
