package io.github.yearnlune.graphql.example.domain

import io.github.yearnlune.graphql.example.graphql.PageInput
import io.github.yearnlune.graphql.example.graphql.SearchInput
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.query.Criteria
import java.util.regex.Pattern
import kotlin.math.floor


fun PageInput.toPageRequest(): Pageable {
    return PageRequest.of(
        this.pageNumber.toInt(),
        this.pageSize.toInt(),
        Sort.by(this.sort.map {
            Sort.Order(if (it.isDescending) Sort.Direction.DESC else Sort.Direction.ASC, it.property)
        })
    )
}

fun String.snakeCase(): String {
    return "(?<=[a-zA-Z])[A-Z]".toRegex().replace(this) {
        "_${it.value}"
    }.lowercase()
}

fun Criteria.search(searches: List<SearchInput>, snakeCase: Boolean = false): Criteria {
    searches.forEach {
        val searchBy = if (snakeCase) it.by.snakeCase() else it.by
        val typedValue = it.value.map { value -> value.toType(it.type) }

        when (it.operator) {
            "REGEX" -> this.and(searchBy).regex(Pattern.compile(typedValue.first() as String, Pattern.CASE_INSENSITIVE or Pattern.UNICODE_CASE))
            "IN" -> this.and(searchBy).`in`(it.value)
            "BETWEEN" -> if (searchBy == "_id") this.and(searchBy).gte((typedValue[0] as Long).toObjectId()).and(searchBy)
                .lt((typedValue[1] as Long).toObjectId()) else this.and(searchBy).gte(typedValue[0]).and(searchBy).lt(typedValue[1])
            else -> this
        }
    }

    return this
}

fun Criteria.search(pageInput: PageInput, snakeCase: Boolean = false): Criteria {
    return this.search(pageInput.search, snakeCase)
}

fun Long.toObjectId(): String = floor((this / 1000).toDouble()).toLong().toString(16) + "0000000000000000"

fun String.toType(type: String): Any {
    return when (type.uppercase()) {
        "INTEGER" -> this.toInt()
        "LONG" -> this.toLong()
        "FLOAT" -> this.toFloat()
        "DOUBLE" -> this.toDouble()
        "BOOLEAN" -> this.toBoolean()
        else -> this
    }
}
