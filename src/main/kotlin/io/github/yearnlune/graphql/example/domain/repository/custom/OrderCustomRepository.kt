package io.github.yearnlune.graphql.example.domain.repository.custom

import io.github.yearnlune.graphql.example.graphql.OrderCollection
import io.github.yearnlune.graphql.example.graphql.PageInput
import org.springframework.data.domain.Page


interface OrderCustomRepository {

    fun findOrders(pageInput: PageInput): Page<OrderCollection>
}