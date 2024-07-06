package com.danvento.saaldigitaltest.data.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/*
* Saal Digital Test:
* com.danvento.saaldigitaltest.data.database.entities
* 
* Created by Dan Vento. 
*/

@Entity(
    tableName = "relations",
    foreignKeys = [
        ForeignKey(
            entity = ObjectEntity::class,
            parentColumns = ["id"],
            childColumns = ["parentId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ObjectEntity::class,
            parentColumns = ["id"],
            childColumns = ["childId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class RelationEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val parentId: Int,
    val childId: Int
)