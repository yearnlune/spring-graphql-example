package io.github.yearnlune.graphql.example.service

import io.github.yearnlune.graphql.example.domain.entity.Order
import io.github.yearnlune.graphql.example.domain.repository.OrderRepository
import io.github.yearnlune.graphql.example.graphql.OrderCollection
import io.github.yearnlune.graphql.example.graphql.OrderInput
import io.github.yearnlune.graphql.example.graphql.PageInput
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service


@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val itemService: ItemService
) {

    fun createOrder(order: OrderInput): Order {
        val itemIds = order.items.map { it.itemId }
        val itemMap = itemService.findItemsByIds(itemIds).associateBy { it.id }

        return orderRepository.save(Order(items = order.items.map {
            Order.OrderItem(itemMap[it.itemId]!!, it.quantity)
        }))
    }

    fun findOrders(pageInput: PageInput): Page<OrderCollection> = orderRepository.findOrders(pageInput)
}