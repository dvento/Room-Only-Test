package com.danvento.saaldigitaltest.ui.viemodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danvento.saaldigitaltest.core.utils.emitResult
import com.danvento.saaldigitaltest.domain.AddRelationUseCase
import com.danvento.saaldigitaltest.domain.CreateObjectUseCase
import com.danvento.saaldigitaltest.domain.DeleteObjectUseCase
import com.danvento.saaldigitaltest.domain.DeleteRelationUseCase
import com.danvento.saaldigitaltest.domain.GetAllObjectsUseCase
import com.danvento.saaldigitaltest.domain.GetObjectByIdUseCase
import com.danvento.saaldigitaltest.domain.UpdateObjectUseCase
import com.danvento.saaldigitaltest.domain.model.ObjectItem
import com.danvento.saaldigitaltest.ui.model.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
class ObjectDetailViewModel(
    private val getObjectByIdUseCase: GetObjectByIdUseCase,
    private val createObjectUseCase: CreateObjectUseCase,
    private val deleteObjectUseCase: DeleteObjectUseCase,
    private val updateObjectUseCase: UpdateObjectUseCase,
    private val getAllObjectsUseCase: GetAllObjectsUseCase,
    private val addRelationUseCase: AddRelationUseCase,
    private val deleteRelationUseCase: DeleteRelationUseCase,
) : ViewModel() {

    /*private val _uiState = MutableStateFlow(UIStateTransition.Loading)
    val uiState = _uiState.asStateFlow()
*/
    private val _objectItemState = MutableStateFlow<UIState<ObjectItem>>(UIState.Loading)
    val objectItemState: StateFlow<UIState<ObjectItem>> = _objectItemState.asStateFlow()

    private val _objectListState = MutableStateFlow<UIState<List<ObjectItem>>>(UIState.Loading)
    val objectListState: StateFlow<UIState<List<ObjectItem>>> = _objectListState.asStateFlow()

    private val _relationState = MutableStateFlow<UIState<Unit>>(UIState.Loading)
    val relationState: StateFlow<UIState<Unit>> = _relationState.asStateFlow()

    init {
        getAllObjects()
    }

    fun initializeNewObject() {
        _objectItemState.value = UIState.Success(
            ObjectItem(
                id = null,
                type = "",
                name = "",
                description = "",
                relations = emptyMap()
            )
        )
    }

    private fun getAllObjects() {
        viewModelScope.launch {
            getAllObjectsUseCase().onEach { result ->
                _objectListState.emitResult(result)
            }.launchIn(viewModelScope)
        }
    }

    fun getObjectById(id: Int) {
        viewModelScope.launch {
            _objectItemState.value = UIState.Loading
            val result = getObjectByIdUseCase(id)
            _objectItemState.emitResult(result)
        }
    }

    fun createObject(type: String, title: String, description: String) {
        viewModelScope.launch {
            val objectItem = processOperation(null, type, title, description)
            val result = createObjectUseCase(objectItem)
            _objectItemState.emitResult(result)
        }
    }

    fun updateObject(id: Int, type: String, title: String, description: String) {
        viewModelScope.launch {
            val objectItem = processOperation(id, type, title, description)
            val result = updateObjectUseCase(objectItem)
            _objectItemState.emitResult(result)
        }
    }

    fun deleteObject(id: Int, type: String, title: String, description: String) {
        viewModelScope.launch {
            val objectItem = processOperation(id, type, title, description)
            val result = deleteObjectUseCase(objectItem)
            _objectItemState.emitResult(result)
        }
    }

    fun addRelation(parentId: Int, childId: Int) {
        viewModelScope.launch {
            _relationState.value = UIState.Loading
            val result = addRelationUseCase(parentId, childId)
            _relationState.emitResult(result)
        }
    }

    fun deleteRelation(relationId: Int) {
        viewModelScope.launch {
            _relationState.value = UIState.Loading
            val result = deleteRelationUseCase(relationId)
            _relationState.emitResult(result)
        }
    }

    private fun processOperation(
        id: Int?,
        type: String,
        title: String,
        description: String
    ): ObjectItem {
        _objectItemState.value = UIState.Loading
        return if (id == null) {
            ObjectItem(type = type, name = title, description = description)
        } else {
            ObjectItem(id = id, type = type, name = title, description = description)
        }
    }
}

