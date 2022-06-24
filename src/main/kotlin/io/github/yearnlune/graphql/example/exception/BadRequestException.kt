package io.github.yearnlune.graphql.example.exception

class BadRequestException(

    @JvmField
    @Suppress("INAPPLICABLE_JVM_FIELD")
    override val message: String? = "Bad Request"
) : GraphqlException(10, message)  {

    override fun getMessage(): String? {
        return super.getMessage()
    }
}