package com.art.paybackapp.common

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.BehaviorSubject

fun <T: Any> Single<T>.asyncToMain(
    schedulers: AppSchedulers
): Single<T> = subscribeOn(schedulers.io()).observeOn(schedulers.main())

fun <T: Any> Single<T>.async(
    schedulers: AppSchedulers
): Single<T> = subscribeOn(schedulers.io()).observeOn(schedulers.io())

fun <T: Any> Observable<T>.asyncToMain(
    schedulers: AppSchedulers
): Observable<T> = subscribeOn(schedulers.io()).observeOn(schedulers.main())

fun <T: Any> Observable<T>.async(
    schedulers: AppSchedulers
): Observable<T> = subscribeOn(schedulers.io()).observeOn(schedulers.io())

val <T> BehaviorSubject<T>?.lastValue: T?
    get() = if (this != null && hasValue()) value else null