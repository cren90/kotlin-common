package com.cren90.kotlin.common.events

import com.cren90.eventbus.EventBus
import kotlinx.coroutines.flow.Flow

@Suppress("unused", "OPT_IN_USAGE")
object EventProvider {

    fun post(event: Any) {
        EventBus.post(event)
    }

    fun getEvents(includeCurrentValue: Boolean = false): Flow<Any> =
        EventBus.getEvents(includeCurrentValue)
}