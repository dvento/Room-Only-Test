package com.danvento.saaldigitaltest.core

import android.content.Context
import androidx.room.Room
import com.danvento.saaldigitaltest.data.database.ObjectDatabase
import org.koin.dsl.module

/*
* Saal Digital Test:
* com.danvento.saaldigitaltest.core
* 
* Created by Dan Vento. 
*/


private const val DATABASE_NAME = "object_database"

fun provideRoom(context: Context): ObjectDatabase {
    return Room.databaseBuilder(
        context.applicationContext,
        ObjectDatabase::class.java,
        DATABASE_NAME
    ).build()
}

fun provideObjectDao(database: ObjectDatabase) = database.objectDao()
fun provideRelationDao(database: ObjectDatabase) = database.relationDao()