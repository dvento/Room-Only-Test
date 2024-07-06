package com.danvento.saaldigitaltest.data.repository

import com.danvento.saaldigitaltest.data.model.DataResult
import com.danvento.saaldigitaltest.domain.model.ObjectItem
import kotlinx.coroutines.flow.Flow

/*
* Saal Digital Test:
* com.danvento.saaldigitaltest.data
* 
* Created by Dan Vento. 
*/

interface ObjectRepository {
    suspend fun getAllObjects(): Flow<DataResult<List<ObjectItem>>>
    suspend fun getObjectById(id: Int): DataResult<ObjectItem>
    suspend fun createObject(objectItem: ObjectItem): DataResult<ObjectItem>
    suspend fun updateObject(objectItem: ObjectItem): DataResult<ObjectItem>
    suspend fun deleteObject(objectItem: ObjectItem): DataResult<ObjectItem>
    suspend fun addRelation(parentId: Int, childId: Int): DataResult<Unit>
    suspend fun deleteRelation(relationId: Int): DataResult<Unit>
}