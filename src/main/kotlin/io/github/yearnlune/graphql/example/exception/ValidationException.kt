package io.github.yearnlune.graphql.example.exception

import graphql.ErrorClassification
import graphql.ErrorType

class ValidationException(
    @JvmField
    @Suppress("INAPPLICABLE_JVM_FIELD")
    override val message: String? = "Validation failed"
) : GraphqlException(11, message) {

    override fun getMessage(): String? {
        return super.getMessage()
    }

    override fun getErrorType(): ErrorClassification {
        return ErrorType.ValidationError
    }
}