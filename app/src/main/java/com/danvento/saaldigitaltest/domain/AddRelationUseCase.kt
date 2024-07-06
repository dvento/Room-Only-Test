package com.danvento.saaldigitaltest.domain

import com.danvento.saaldigitaltest.data.model.DataResult
import com.danvento.saaldigitaltest.data.repository.ObjectRepository

/*
* Saal Digital Test:
* com.danvento.saaldigitaltest.domain
* 
* Created by Dan Vento. 
*/

class AddRelationUseCase(
    private val objectRepository: ObjectRepository
) {
    suspend operator fun invoke(parentId: Int, childId: Int): DataResult<Unit> =
        objectRepository.addRelation(parentId, childId)


}