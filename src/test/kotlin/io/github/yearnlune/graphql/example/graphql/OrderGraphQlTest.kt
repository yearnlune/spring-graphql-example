package io.github.yearnlune.graphql.example.graphql

import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class OrderGraphQlTest : GraphQlTestSupport() {

    @Test
    @DisplayName("[GRAPHQL|MUTATION] 주문 생성하기")
    fun orderCreate() {
        /* GIVEN */
        val query = "mutation {\n" +
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
                "}"

        /* WHEN */
        val response = graphQlTester
            .document(query).execute()
            .path("orderCreate").hasValue().entity(OrderCollection::class.java)

        /* THEN */
        MatcherAssert.assertThat(response.get().items?.sumOf { it.item.price * it.quantity }, CoreMatchers.`is`(Matchers.closeTo(26.65, 0.001)))
    }

    @Test
    @DisplayName("[GRAPHQL|MUTATION] 주문 생성하기 with Operation")
    fun orderCreateWithOperation() {
        /* GIVEN */
        val query = "mutation OrderCreate(\$order: OrderInput){\n" +
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
                "}"
        val variable = OrderInput.builder()
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

        /* WHEN */
        val response = graphQlTester.document(query)
            .operationName("OrderCreate")
            .variable("order", variable.toMap())
            .execute()
            .path("orderCreate").entity(OrderCollection::class.java)

        /* THEN */
        MatcherAssert.assertThat(response.get().items?.sumOf { it.item.price * it.quantity }, CoreMatchers.`is`(Matchers.closeTo(26.65, 0.001)))
    }
}