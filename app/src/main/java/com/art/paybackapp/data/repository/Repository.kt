package com.art.paybackapp.data.repository

interface Repository<T> {

    fun <T> get(): T

    fun <T> save(element: T)

}