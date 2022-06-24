package io.github.yearnlune.graphql.example.domain.repository.custom.impl

import io.github.yearnlune.graphql.example.domain.repository.custom.ItemCustomRepository
import io.github.yearnlune.graphql.example.domain.search
import io.github.yearnlune.graphql.example.domain.toPageRequest
import io.github.yearnlune.graphql.example.graphql.ItemCollection
import io.github.yearnlune.graphql.example.graphql.PageInput
import org.springframework.data.domain.Page
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.support.PageableExecutionUtils

class ItemCustomRepositoryImpl(
    private val mongoTemplate: MongoTemplate
) : ItemCustomRepository {

    override fun findItems(pageInput: PageInput): Page<ItemCollection> {
        val pageable = pageInput.toPageRequest()
        val expression = Criteria.where("deleted").exists(false).search(pageInput, true)
        val query = Query(expression).with(pageable)
        val items = mongoTemplate.find(query, ItemCollection::class.java)

        return PageableExecutionUtils.getPage(
            items, pageable
        ) {
            mongoTemplate.count(
                Query.of(query).limit(-1).skip(-1), ItemCollection::class.java
            )
        }
    }
}