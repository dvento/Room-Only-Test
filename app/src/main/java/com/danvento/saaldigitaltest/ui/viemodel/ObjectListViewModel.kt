package com.danvento.saaldigitaltest.ui.viemodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danvento.saaldigitaltest.core.utils.emitResult
import com.danvento.saaldigitaltest.data.model.DataResult
import com.danvento.saaldigitaltest.domain.CreateObjectUseCase
import com.danvento.saaldigitaltest.domain.DeleteObjectUseCase
import com.danvento.saaldigitaltest.domain.GetAllObjectsUseCase
import com.danvento.saaldigitaltest.domain.model.ObjectItem
import com.danvento.saaldigitaltest.ui.model.UIState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/*
* Saal Digital Test:
* com.danvento.saaldigitaltest.ui.viemodel
* 
* Created by Dan Vento. 
*/

class ObjectListViewModel(
    private val getAllObjectsUseCase: GetAllObjectsUseCase,
    private val deleteObjectUseCase: DeleteObjectUseCase,
) : ViewModel() {
    private val _objectList = MutableStateFlow<UIState<List<ObjectItem>>>(UIState.Loading)
    val objectListFlow: StateFlow<UIState<List<ObjectItem>>> = _objectList.asStateFlow()

    init {
        getAllObjects()
    }

    private fun getAllObjects() {

        viewModelScope.launch {
            getAllObjectsUseCase().onEach { result ->
                _objectList.emitResult(result)
            }.launchIn(viewModelScope)
        }
    }

    fun deleteObject(obj: ObjectItem) {
        viewModelScope.launch {
             deleteObjectUseCase(obj)
        }
    }



}