package io.github.yearnlune.graphql.example.graphql

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

fun Any.toMap(): Map<*, *>? {
    val objectMapper = jacksonObjectMapper()
    return objectMapper.readValue(objectMapper.writeValueAsString(this), Map::class.java)
}

fun <T> Map<*, *>.toGraphQlObject(targetClass: Class<T>): T {
    val objectMapper = jacksonObjectMapper()
    return objectMapper.readValue(objectMapper.writeValueAsString(this.values.first()), targetClass)
}