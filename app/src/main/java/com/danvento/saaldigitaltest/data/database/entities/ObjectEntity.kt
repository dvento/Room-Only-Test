package com.danvento.saaldigitaltest.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/*
* Saal Digital Test:
* com.danvento.saaldigitaltest.data.database.entities
* 
* Created by Dan Vento. 
*/


@Entity(tableName = "objects")
data class ObjectEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String?,
    val type: String
)