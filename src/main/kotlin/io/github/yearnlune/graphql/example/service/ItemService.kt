package io.github.yearnlune.graphql.example.service

import io.github.yearnlune.graphql.example.domain.entity.Item
import io.github.yearnlune.graphql.example.domain.repository.ItemReactiveRepository
import io.github.yearnlune.graphql.example.domain.repository.ItemRepository
import io.github.yearnlune.graphql.example.graphql.ItemCollection
import io.github.yearnlune.graphql.example.graphql.ItemInput
import io.github.yearnlune.graphql.example.graphql.PageInput
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class ItemService(
    private val itemRepository: ItemRepository,
    private val itemReactiveRepository: ItemReactiveRepository
) {

    fun createItem(item: ItemInput): Item {
        return itemRepository.save(Item(name = item.name, price = item.price))
    }

    fun findItems(page: PageInput): Page<ItemCollection> = itemRepository.findItems(page)

    fun findItemsByIds(ids: List<String>): List<Item> = itemRepository.findAllById(ids).toList()

    fun findItems(): Flux<ItemCollection> = itemReactiveRepository.findItems()

}
