package com.art.paybackapp.common

abstract class Mapper<X, Y> {

    abstract fun mapFrom(from: X): Y

    abstract fun mapTo(to: Y): X

}