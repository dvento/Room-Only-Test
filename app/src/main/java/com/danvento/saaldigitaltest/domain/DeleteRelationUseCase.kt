package com.danvento.saaldigitaltest.domain

import com.danvento.saaldigitaltest.data.model.DataResult
import com.danvento.saaldigitaltest.data.repository.ObjectRepository

/*
* Saal Digital Test:
* com.danvento.saaldigitaltest.domain
* 
* Created by Dan Vento. 
*/

class DeleteRelationUseCase (
    private val objectRepository: ObjectRepository
) {
    suspend operator fun invoke(relationId: Int): DataResult<Unit> =
        objectRepository.deleteRelation(relationId)

}