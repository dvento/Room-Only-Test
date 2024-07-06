package com.danvento.saaldigitaltest.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Relation
import com.danvento.saaldigitaltest.data.database.entities.RelationEntity

/*
* Saal Digital Test:
* com.danvento.saaldigitaltest.data.database.dao
* 
* Created by Dan Vento. 
*/
@Dao
interface RelationDao {

    @Query("SELECT * FROM relations WHERE parentId = :objectId")
    suspend fun getChildRelationsForId(objectId: Int): List<RelationEntity>

    @Query("SELECT * FROM relations WHERE childId = :objectId")
    suspend fun getParentRelationsForId(objectId: Int): List<RelationEntity>
    @Query("SELECT * FROM relations WHERE id = :parentId")
    suspend fun getRelationsForParent(parentId: Int): List<RelationEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRelation(relation: RelationEntity)

    @Query("DELETE FROM relations WHERE id = :relationId")
    suspend fun deleteRelation(relationId: Int)
}