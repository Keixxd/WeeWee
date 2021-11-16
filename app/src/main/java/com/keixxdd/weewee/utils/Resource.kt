package com.keixxdd.weewee.utils

sealed class Resource<T>(val data: T?,val message: Throwable?) {
    class Success<T>(data:T): Resource<T>(data, null)
    class Failure<T>(message: Throwable?): Resource<T>(null, message)
}