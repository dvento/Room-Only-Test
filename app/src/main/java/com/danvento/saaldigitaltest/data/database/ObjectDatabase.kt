package com.danvento.saaldigitaltest.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.danvento.saaldigitaltest.data.database.dao.ObjectDao
import com.danvento.saaldigitaltest.data.database.dao.RelationDao
import com.danvento.saaldigitaltest.data.database.entities.ObjectEntity
import com.danvento.saaldigitaltest.data.database.entities.RelationEntity

/*
* Saal Digital Test:
* com.danvento.saaldigitaltest.data.database
* 
* Created by Dan Vento. 
*/

@Database(entities = [ObjectEntity::class, RelationEntity::class], version = 1)
abstract class ObjectDatabase : RoomDatabase() {
    abstract fun objectDao(): ObjectDao
    abstract fun relationDao(): RelationDao
}