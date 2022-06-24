package io.github.yearnlune.graphql.example.graphql

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.graphql.test.tester.GraphQlTester
import org.springframework.test.context.ActiveProfiles

@Suppress("SpringJavaInjectionPointsAutowiringInspection")
@SpringBootTest
@AutoConfigureGraphQlTester
@ActiveProfiles("test")
abstract class GraphQlTestSupport {

    @Autowired
    protected lateinit var graphQlTester: GraphQlTester
}