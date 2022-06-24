package io.github.yearnlune.graphql.example.domain.repository.custom.impl

import io.github.yearnlune.graphql.example.domain.repository.custom.OrderCustomRepository
import io.github.yearnlune.graphql.example.domain.search
import io.github.yearnlune.graphql.example.domain.toPageRequest
import io.github.yearnlune.graphql.example.graphql.OrderCollection
import io.github.yearnlune.graphql.example.graphql.PageInput
import org.springframework.data.domain.Page
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.support.PageableExecutionUtils

class OrderCustomRepositoryImpl(
    private val mongoTemplate: MongoTemplate
) : OrderCustomRepository {

    override fun findOrders(pageInput: PageInput): Page<OrderCollection> {
        val pageable = pageInput.toPageRequest()
        val expression = Criteria.where("deleted").exists(false).search(pageInput, true)
        val query = Query(expression).with(pageable)
        val orders = mongoTemplate.find(query, OrderCollection::class.java, "orders")

        return PageableExecutionUtils.getPage(
            orders, pageable
        ) {
            mongoTemplate.count(
                Query.of(query).limit(-1).skip(-1), OrderCollection::class.java, "orders"
            )
        }
    }
}