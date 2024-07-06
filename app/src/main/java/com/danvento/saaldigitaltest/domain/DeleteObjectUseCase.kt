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

open class DeleteObjectUseCase (
    private val objectRepository: ObjectRepository
) {
    suspend operator fun invoke(objectItem: ObjectItem): DataResult<ObjectItem> =
        objectRepository.deleteObject(objectItem)

}