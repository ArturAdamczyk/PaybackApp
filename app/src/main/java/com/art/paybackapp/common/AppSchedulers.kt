package com.art.paybackapp.common

import io.reactivex.rxjava3.core.Scheduler

interface AppSchedulers {
    fun io(): Scheduler
    fun main(): Scheduler
}