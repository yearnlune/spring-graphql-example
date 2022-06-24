package io.github.yearnlune.graphql.example.controller

import io.github.yearnlune.graphql.example.graphql.GraphqlForm
import io.github.yearnlune.graphql.example.graphql.OrderCollection
import io.github.yearnlune.graphql.example.graphql.OrderInput
import io.github.yearnlune.graphql.example.graphql.OrderItemInput
import io.github.yearnlune.graphql.example.graphql.OrderPagination
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.hamcrest.Matchers.closeTo
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class OrderControllerTest : ControllerTestSupport() {

    @Test
    @DisplayName("[GRAPHQL OVER HTTP|QUERY] 주문목록 가져오기")
    fun ordersGet() {
        /* GIVEN */
        val requestBody = GraphqlForm.Request(
            query = "query {\n" +
                    "    ordersGet(page: {\n" +
                    "        pageNumber: 1\n" +
                    "        pageSize: 2\n" +
                    "        sort: [{\n" +
                    "            property: \"_id\"\n" +
                    "            isDescending: false\n" +
                    "        }]\n" +
                    "        search: []\n" +
                    "    }) {\n" +
                    "        orders {\n" +
                    "            id\n" +
                    "            items {\n" +
                    "                item {\n" +
                    "                    id\n" +
                    "                    name\n" +
                    "                    price\n" +
                    "                }\n" +
                    "                quantity\n" +
                    "            }\n" +
                    "        }\n" +
                    "        total\n" +
                    "    }\n" +
                    "}",
        )

        /* WHEN */
        val response = postGraphql(requestBody)
        val orderPagination = getGraphqlResponseBody("ordersGet", response.response.contentAsString, OrderPagination::class.java)

        /* THEN */
        MatcherAssert.assertThat(orderPagination?.orders?.first()?.id, Matchers.`is`("62946b347bfac12ff5f2ea60"))
    }

    @Test
    @DisplayName("[GRAPHQL OVER HTTP|MUTATION] 주문 생성하기")
    fun orderCreate() {
        /* GIVEN */
        val requestBody = GraphqlForm.Request(
            query = "mutation {\n" +
                    "    orderCreate(order: {\n" +
                    "        items: [{\n" +
                    "            itemId: \"62946b347bfac12ff5f2ea56\"\n" +
                    "            quantity: 1\n" +
                    "        }, {\n" +
                    "            itemId: \"62946b347bfac12ff5f2ea57\"\n" +
                    "            quantity: 2\n" +
                    "        }]\n" +
                    "    }) {\n" +
                    "        id\n" +
                    "        items {\n" +
                    "            item {\n" +
                    "                id\n" +
                    "                name\n" +
                    "                price\n" +
                    "            }\n" +
                    "            quantity\n" +
                    "        }\n" +
                    "    }\n" +
                    "}",
        )

        /* WHEN */
        val response = postGraphql(requestBody)
        val order = getGraphqlResponseBody("orderCreate", response.response.contentAsString, OrderCollection::class.java)

        /* THEN */
        MatcherAssert.assertThat(order?.items?.sumOf { it.item.price * it.quantity }, CoreMatchers.`is`(closeTo(26.65, 0.001)))
    }

    @Test
    @DisplayName("[GRAPHQL OVER HTTP|MUTATION] 주문 생성하기 with Operation")
    fun orderCreate_withOperation() {
        /* GIVEN */
        val requestBody = GraphqlForm.Request(
            query = "mutation OrderCreate(\$order: OrderInput){\n" +
                    "    orderCreate(order: \$order) {\n" +
                    "        id\n" +
                    "        items {\n" +
                    "            item {\n" +
                    "                id\n" +
                    "                name\n" +
                    "                price\n" +
                    "            }\n" +
                    "            quantity\n" +
                    "        }\n" +
                    "    }\n" +
                    "}",
            operationName = "OrderCreate",
            variables = mapOf(
                Pair(
                    "order", OrderInput.builder()
                        .withItems(
                            listOf(
                                OrderItemInput.builder()
                                    .withItemId("62946b347bfac12ff5f2ea56")
                                    .withQuantity(1)
                                    .build(),
                                OrderItemInput.builder()
                                    .withItemId("62946b347bfac12ff5f2ea57")
                                    .withQuantity(2)
                                    .build()
                            )
                        )
                        .build()
                )
            )
        )

        /* WHEN */
        val response = postGraphql(requestBody)
        val order = getGraphqlResponseBody("orderCreate", response.response.contentAsString, OrderCollection::class.java)

        /* THEN */
        MatcherAssert.assertThat(order?.items?.sumOf { it.item.price * it.quantity }, CoreMatchers.`is`(closeTo(26.65, 0.001)))
    }
}