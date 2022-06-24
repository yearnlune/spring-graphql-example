package io.github.yearnlune.graphql.example.controller

import io.github.yearnlune.graphql.example.domain.entity.Order
import io.github.yearnlune.graphql.example.graphql.OrderInput
import io.github.yearnlune.graphql.example.graphql.OrderPagination
import io.github.yearnlune.graphql.example.graphql.PageInput
import io.github.yearnlune.graphql.example.service.OrderService
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller


@Controller
class OrderController(
    private val orderService: OrderService
) {

    @QueryMapping("ordersGet")
    fun findOrders(@Argument page: PageInput): OrderPagination {
        val orders = orderService.findOrders(page)

        return OrderPagination.builder()
            .withOrders(orders.content)
            .withTotal(orders.totalElements)
            .build()
    }

    @MutationMapping("orderCreate")
    fun createOrder(@Argument order: OrderInput): Order = orderService.createOrder(order)
}