package io.github.yearnlune.graphql.example.config

import graphql.GraphQLError
import graphql.schema.DataFetchingEnvironment
import io.github.yearnlune.graphql.example.exception.BadRequestException
import io.github.yearnlune.graphql.example.exception.ValidationException
import org.springframework.context.annotation.Configuration
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter


@Configuration
class GraphqlExceptionConfig : DataFetcherExceptionResolverAdapter() {

    override fun resolveToSingleError(ex: Throwable, env: DataFetchingEnvironment): GraphQLError? {
        return when (ex) {
            is ValidationException -> ValidationException(ex.message)
            is IllegalArgumentException -> BadRequestException(ex.message)
            else -> super.resolveToSingleError(ex, env)
        }
    }
}