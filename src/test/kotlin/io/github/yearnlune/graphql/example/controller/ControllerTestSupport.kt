package io.github.yearnlune.graphql.example.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.github.yearnlune.graphql.example.graphql.GraphqlForm
import org.hamcrest.CoreMatchers
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@Suppress("SpringJavaInjectionPointsAutowiringInspection")
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
abstract class ControllerTestSupport {

    companion object {
        val objectMapper: ObjectMapper = jacksonObjectMapper()
    }

    @Autowired
    protected lateinit var mockMvc: MockMvc

    @Value("\${spring.graphql.path}")
    private lateinit var graphqlUrl: String

    protected fun postGraphql(requestBody: GraphqlForm.Request): MvcResult {
        val requestResult = mockMvc
            .perform(
                MockMvcRequestBuilders.post(graphqlUrl)
                    .content(objectMapper.writeValueAsBytes(requestBody))
                    .contentType(MediaType.APPLICATION_GRAPHQL)
                    .characterEncoding("UTF-8")
            ).andExpect {
                MockMvcResultMatchers.request().asyncStarted()
                MockMvcResultMatchers.request().asyncResult(CoreMatchers.notNullValue())
            }.andReturn()

        return mockMvc
            .perform(
                MockMvcRequestBuilders.asyncDispatch(requestResult)
            )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()
    }

    protected fun getGraphqlResponseBody(name: String, response: String): Any? = objectMapper.readValue(response, GraphqlForm.Response::class.java).data[name]

    protected fun <T> getGraphqlResponseBody(name: String, response: String, clazz: Class<T>): T? =
        getGraphqlResponseBody(name, response)?.let { objectMapper.readValue(objectMapper.writeValueAsString(it), clazz) }
}