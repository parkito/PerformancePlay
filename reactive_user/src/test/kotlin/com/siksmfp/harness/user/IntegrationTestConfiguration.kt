package com.siksmfp.harness.user

import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.utility.DockerImageName.parse
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@TestConfiguration
class IntegrationTestConfiguration {

    private val mongoDBContainer = MongoDBContainer(parse("mongo:4.4.11"))
        .withExposedPorts(27017)

    @PostConstruct
    fun init() {
        mongoDBContainer.start()
    }

    @PreDestroy
    fun destroy() {
        mongoDBContainer.stop()
    }

    @Bean
    fun mongoClient(): MongoClient {
        return MongoClients.create(
            mongoDBContainer.replicaSetUrl
        )
    }
}
