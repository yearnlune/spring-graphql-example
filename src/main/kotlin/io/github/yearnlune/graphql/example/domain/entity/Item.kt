package io.github.yearnlune.graphql.example.domain.entity

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document


@Document("items")
data class Item(

    @Id
    val id: String = ObjectId().toString(),

    @Indexed
    val name: String,

    val price: Double,

    val deleted: Boolean? = null
)