package io.github.yearnlune.graphql.example.domain.repository

import io.github.yearnlune.graphql.example.domain.entity.Item
import io.github.yearnlune.graphql.example.graphql.ItemCollection
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface ItemReactiveRepository : ReactiveMongoRepository<Item, String> {

    @Query(value = "{'deleted': {\$exists:  false}}")
    fun findItems(): Flux<ItemCollection>
}