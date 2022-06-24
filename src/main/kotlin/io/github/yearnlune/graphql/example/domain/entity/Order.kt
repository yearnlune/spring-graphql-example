package io.github.yearnlune.graphql.example.domain.entity

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document


@Document("orders")
data class Order(

    @Id
    val id: String = ObjectId().toString(),

    val items: List<OrderItem>,

    val deleted: Boolean? = null
) {

    data class OrderItem(

        val item: Item,

        val quantity: Long
    )
}