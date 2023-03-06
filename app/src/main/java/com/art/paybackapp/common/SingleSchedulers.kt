package com.art.paybackapp.common

import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

class SingleSchedulers : AppSchedulers {

    override fun io(): Scheduler = Schedulers.single()

    override fun main(): Scheduler = Schedulers.single()

}