package com.danvento.saaldigitaltest.core.utils

/*
* Saal Digital Test:
* com.danvento.saaldigitaltest.core.utils
* 
* Created by Dan Vento. 
*/

import com.danvento.saaldigitaltest.data.model.DataResult
import com.danvento.saaldigitaltest.ui.model.UIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow

// Extension function to handle DataResult and emit UIState
suspend fun <T> MutableSharedFlow<UIState<T>>.emitResult(result: DataResult<T>) {
    when (result) {
        is DataResult.Success -> emit(UIState.Success(result.data))
        is DataResult.Error -> emit(UIState.Error(result.exception.message))
    }
}

// For Flows
fun <T> Flow<DataResult<T>>.toUiStateFlow(): Flow<UIState<T>> = flow {
    collect { result ->
        when (result) {
            is DataResult.Success -> emit(UIState.Success(result.data))
            is DataResult.Error -> emit(UIState.Error(result.exception.message))
        }
    }
}

