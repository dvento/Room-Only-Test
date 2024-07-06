package com.danvento.saaldigitaltest.domain

import com.danvento.saaldigitaltest.data.model.DataResult
import com.danvento.saaldigitaltest.data.repository.ObjectRepository
import com.danvento.saaldigitaltest.domain.model.ObjectItem
import kotlinx.coroutines.flow.Flow

/*
* Saal Digital Test:
* com.danvento.saaldigitaltest.domain
* 
* Created by Dan Vento. 
*/

class GetAllObjectsUseCase(
    private val repository: ObjectRepository
) {
    suspend operator fun invoke(): Flow<DataResult<List<ObjectItem>>> = repository.getAllObjects()
}