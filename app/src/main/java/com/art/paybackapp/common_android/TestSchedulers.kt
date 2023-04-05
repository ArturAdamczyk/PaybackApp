package com.art.paybackapp.common_android

import com.art.paybackapp.common.AppSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.TestScheduler

class TestSchedulers(
    internal val scheduler: TestScheduler = TestScheduler()
) : AppSchedulers {

    override fun io(): Scheduler = scheduler

    override fun main(): Scheduler = scheduler

}