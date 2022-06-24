package io.github.yearnlune.graphql.example.domain.repository.custom

import io.github.yearnlune.graphql.example.graphql.ItemCollection
import io.github.yearnlune.graphql.example.graphql.PageInput
import org.springframework.data.domain.Page

interface ItemCustomRepository {

    fun findItems(pageInput: PageInput): Page<ItemCollection>
}