package com.danvento.saaldigitaltest.domain.model

/*
* Saal Digital Test:
* com.danvento.saaldigitaltest.data.model
* 
* Created by Dan Vento. 
*/

data class ObjectItem(
    val id: Int? = null,
    val name: String,
    val type: String,
    val description: String? = null,
    val relations: Map<Int, ObjectItem>? = null,
)