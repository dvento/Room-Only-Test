package com.danvento.saaldigitaltest.data.repository

import com.danvento.saaldigitaltest.data.database.dao.ObjectDao
import com.danvento.saaldigitaltest.data.database.dao.RelationDao
import com.danvento.saaldigitaltest.data.database.entities.ObjectEntity
import com.danvento.saaldigitaltest.data.database.entities.RelationEntity
import com.danvento.saaldigitaltest.data.model.DataResult
import com.danvento.saaldigitaltest.domain.model.ObjectItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

/*
* Saal Digital Test:
* com.danvento.saaldigitaltest.data.repository
* 
* Created by Dan Vento. 
*/

class ObjectRepositoryImpl(
    private val objectDao: ObjectDao,
    private val relationDao: RelationDao
) : ObjectRepository {

    override suspend fun getAllObjects(): Flow<DataResult<List<ObjectItem>>> {
        return objectDao.getObjects()
            .map { entities ->
                DataResult.Success(entities.map { toObjectItem(it) })
            }
            .catch { e ->
                DataResult.Error(e)
            }
    }

    override suspend fun getObjectById(id: Int): DataResult<ObjectItem> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val objectEntity = objectDao.getObjectById(id)
                DataResult.Success(toObjectItem(objectEntity))
            } catch (e: Exception) {
                DataResult.Error(e)
            }
        }


    override suspend fun createObject(objectItem: ObjectItem): DataResult<ObjectItem> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val id = objectDao.insertObject(toObjectEntity(objectItem))
                DataResult.Success(objectItem.copy(id = id.toInt()))
            } catch (e: Exception) {
                DataResult.Error(e)
            }
        }

    override suspend fun updateObject(objectItem: ObjectItem): DataResult<ObjectItem> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                objectDao.updateObject(toObjectEntity(objectItem))
                DataResult.Success(objectItem)
            } catch (e: Exception) {
                DataResult.Error(e)
            }
        }

    override suspend fun deleteObject(objectItem: ObjectItem): DataResult<ObjectItem> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                objectDao.deleteObject(toObjectEntity(objectItem))
                DataResult.Success(objectItem)
            } catch (e: Exception) {
                DataResult.Error(e)
            }
        }

    override suspend fun addRelation(parentId: Int, childId: Int): DataResult<Unit> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val relation = RelationEntity(parentId = parentId, childId = childId)
                relationDao.insertRelation(relation)
                DataResult.Success(Unit)
            } catch (e: Exception) {
                DataResult.Error(e)
            }
        }

    override suspend fun deleteRelation(relationId: Int): DataResult<Unit> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                relationDao.deleteRelation(relationId)
                DataResult.Success(Unit)
            } catch (e: Exception) {
                DataResult.Error(e)
            }
        }

    // Not for production use
    private suspend fun toObjectItem(obj: ObjectEntity, processedIds: MutableSet<Int> = mutableSetOf()): ObjectItem {
        // Add current object id to processed set to avoid infinite recursion
        if (!processedIds.add(obj.id)) {
            return ObjectItem(
                id = obj.id,
                name = obj.name,
                type = obj.type,
                description = obj.description,
                relations = emptyMap()
            )
        }

        // Fetch child relations
        val childRelations = relationDao.getChildRelationsForId(obj.id)
        val childObjectsMap = childRelations.associate { relation ->
            val relatedObject = objectDao.getObjectById(relation.childId)
            relation.id to toObjectItem(relatedObject, processedIds)
        }

        // Fetch parent relations
        val parentRelations = relationDao.getParentRelationsForId(obj.id)
        val parentObjectsMap = parentRelations.associate { relation ->
            val relatedObject = objectDao.getObjectById(relation.parentId)
            relation.id to toObjectItem(relatedObject, processedIds)
        }

        // Combine child and parent objects
        val relatedObjectsMap = childObjectsMap + parentObjectsMap

        return ObjectItem(
            id = obj.id,
            name = obj.name,
            type = obj.type,
            description = obj.description,
            relations = relatedObjectsMap
        )
    }

    private fun toObjectEntity(objectItem: ObjectItem): ObjectEntity {
        return if (objectItem.id == null) {
            ObjectEntity(
                name = objectItem.name,
                type = objectItem.type,
                description = objectItem.description
            )
        } else {
            ObjectEntity(
                id = objectItem.id,
                name = objectItem.name,
                type = objectItem.type,
                description = objectItem.description
            )
        }

    }
}