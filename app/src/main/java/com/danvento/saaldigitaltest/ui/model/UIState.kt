package com.danvento.saaldigitaltest.ui.model

/*
* Saal Digital Test:
* com.danvento.saaldigitaltest.ui.model
* 
* Created by Dan Vento. 
*/

sealed class UIState<out T> {
    data object Loading : UIState<Nothing>()
    data class Success<out T>(val data: T) : UIState<T>()
    data class Error(val message: String?) : UIState<Nothing>()
}

/*
sealed class UIStateTransition<out T> {
    data object Loading : UIState<Nothing>()
    data class Error(val message: String?) : UIState<Nothing>()
}*/
