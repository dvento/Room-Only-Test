package com.danvento.saaldigitaltest.data.model

/*
* Saal Digital Test:
* com.danvento.saaldigitaltest.data.model
* 
* Created by Dan Vento. 
*/

sealed class DataResult<out T> {
    data class Success<out T>(val data: T) : DataResult<T>()
    data class Error(val exception: Throwable) : DataResult<Nothing>()
}