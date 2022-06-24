package io.github.yearnlune.graphql.example.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.core.io.Resource
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean

@Configuration
@Profile("!test")
class MongoConfig {

    @Value("classpath:data.json")
    private lateinit var testData: Resource

    @Bean
    @Suppress("SpringJavaInjectionPointsAutowiringInspection")
    fun repositoryPopulator(objectMapper: ObjectMapper): Jackson2RepositoryPopulatorFactoryBean {
        val factory = Jackson2RepositoryPopulatorFactoryBean()

        factory.setMapper(objectMapper)
        factory.setResources(arrayOf(testData))
        return factory
    }
}