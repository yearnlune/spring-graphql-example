package io.github.yearnlune.graphql.example.exception

import graphql.ErrorClassification
import graphql.GraphQLError
import graphql.language.SourceLocation

abstract class GraphqlException(

    private val errorCode: Int = 0,

    @JvmField
    @Suppress("INAPPLICABLE_JVM_FIELD")
    override val message: String?
) : GraphQLError, RuntimeException(message) {

    override fun getMessage(): String? = message

    override fun getLocations(): MutableList<SourceLocation>? = null

    override fun getErrorType(): ErrorClassification? = null

    override fun getExtensions(): MutableMap<String, Any> {
        return mutableMapOf(
            Pair("code", this.errorCode),
            Pair("exception", this.javaClass.simpleName)
        )
    }
}