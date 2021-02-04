package com.amgdeveloper.cookingapp.common

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * Created by amgdeveloper on 13/12/2020
 */
interface Scope : CoroutineScope {

    class Impl(override val uiDispatcher: CoroutineDispatcher): Scope {
        override lateinit var job: Job
    }

    var job: Job
    val uiDispatcher : CoroutineDispatcher

    override val coroutineContext: CoroutineContext
        get() = uiDispatcher + job

    fun initScope() {
        job = SupervisorJob()
    }

    fun destroyScope() {
        job.cancel()
    }
}