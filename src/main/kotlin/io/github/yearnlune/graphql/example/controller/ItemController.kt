package io.github.yearnlune.graphql.example.controller

import io.github.yearnlune.graphql.example.graphql.ItemCollection
import io.github.yearnlune.graphql.example.graphql.ItemInput
import io.github.yearnlune.graphql.example.graphql.ItemPagination
import io.github.yearnlune.graphql.example.graphql.PageInput
import io.github.yearnlune.graphql.example.service.ItemService
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.SubscriptionMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux


@Controller
class ItemController(
    private val itemService: ItemService
) {

    @QueryMapping("itemsGet")
    fun findItems(@Argument page: PageInput): ItemPagination {
        val items = itemService.findItems(page)

        return ItemPagination.builder()
            .withItems(items.content)
            .withTotal(items.totalElements)
            .build()
    }

    @MutationMapping("itemCreate")
    fun createItem(@Argument item: ItemInput) = itemService.createItem(item)

    @SubscriptionMapping("itemsSubscribe")
    fun subscribeItems(): Flux<ItemCollection> = itemService.findItems()
}