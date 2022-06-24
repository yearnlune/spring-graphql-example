package io.github.yearnlune.graphql.example.domain.repository

import io.github.yearnlune.graphql.example.domain.entity.Item
import io.github.yearnlune.graphql.example.domain.repository.custom.ItemCustomRepository
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ItemRepository : MongoRepository<Item, String>, ItemCustomRepository {
}