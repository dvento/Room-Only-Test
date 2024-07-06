package com.danvento.saaldigitaltest.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.danvento.saaldigitaltest.data.database.entities.ObjectEntity
import kotlinx.coroutines.flow.Flow

/*
* Saal Digital Test:
* com.danvento.saaldigitaltest.data.database.dao
* 
* Created by Dan Vento. 
*/

@Dao
interface ObjectDao {
    @Query("SELECT * FROM objects")
    fun getObjects(): Flow<List<ObjectEntity>>

    @Query("SELECT * FROM objects WHERE id = :id")
    suspend fun getObjectById(id: Int): ObjectEntity


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertObject(obj: ObjectEntity): Long

    @Update
    suspend fun updateObject(obj: ObjectEntity)

    @Delete
    suspend fun deleteObject(obj: ObjectEntity)
}