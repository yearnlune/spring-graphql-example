package io.github.yearnlune.graphql.example.domain.repository

import io.github.yearnlune.graphql.example.domain.entity.Order
import io.github.yearnlune.graphql.example.domain.repository.custom.OrderCustomRepository
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository


@Repository
interface OrderRepository : MongoRepository<Order, String>, OrderCustomRepository {
}