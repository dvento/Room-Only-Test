package com.danvento.saaldigitaltest.domain

import com.danvento.saaldigitaltest.data.model.DataResult
import com.danvento.saaldigitaltest.data.repository.ObjectRepository
import com.danvento.saaldigitaltest.domain.model.ObjectItem

/*
* Saal Digital Test:
* com.danvento.saaldigitaltest.domain
* 
* Created by Dan Vento. 
*/

class GetObjectByIdUseCase (
    private val repository: ObjectRepository
) {
    suspend operator fun invoke(id: Int): DataResult<ObjectItem> = repository.getObjectById(id)
}