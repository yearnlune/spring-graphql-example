package io.github.yearnlune.graphql.example.graphql

class GraphqlForm {

    data class Request(

        val query: String,

        val operationName: String? = null,

        val variables: Map<String, Any> = mapOf()
    )

    data class Response(

        val data: Map<String, *>,

        val errors: List<*>?
    )
}